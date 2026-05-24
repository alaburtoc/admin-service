package com.gameup.admin_service.service;

import com.gameup.admin_service.client.UsuarioFeignClient;
import com.gameup.admin_service.dto.*;
import com.gameup.admin_service.exception.BusinessException;
import com.gameup.admin_service.exception.ResourceNotFoundException;
import com.gameup.admin_service.model.Admin;
import com.gameup.admin_service.model.NivelAcceso;
import com.gameup.admin_service.repository.AdminRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    private final PasswordEncoder passwordEncoder;

    public List<AdminResponseDTO> obtenerAdmins() {
        log.info("Obteniendo todos los admins");
        return adminRepository.findAll().stream()
                .map(a -> mapToResponse(a, null))
                .toList();
    }

    public AdminResponseDTO obtenerAdminPorId(Long id) {
        log.info("Buscando admin con id: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no encontrado con id: " + id));
        return mapToResponse(admin, null);
    }

    public List<AdminResponseDTO> obtenerPorNivelAcceso(NivelAcceso nivelAcceso) {
        log.info("Buscando admins con nivel de acceso: {}", nivelAcceso);
        return adminRepository.findByNivelAcceso(nivelAcceso).stream()
                .map(a -> mapToResponse(a, null))
                .toList();
    }

    public List<AdminResponseDTO> obtenerActivos() {
        log.info("Obteniendo admins activos");
        return adminRepository.findByActivo(true).stream()
                .map(a -> mapToResponse(a, null))
                .toList();
    }

    public AdminResponseDTO autenticar(AdminLoginRequest request) {
        log.info("Intentando autenticar admin con id: {}", request.getIdAdmin());

        Admin admin = adminRepository.findById(request.getIdAdmin())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Admin no encontrado con id: " + request.getIdAdmin()));

        if (!passwordEncoder.matches(request.getCredencial(), admin.getCredencial())) {
            throw new BusinessException("Credencial incorrecta");
        }

        if (!admin.getActivo()) {
            throw new BusinessException("El admin está inactivo y no puede iniciar sesión");
        }

        log.info("Admin id {} autenticado correctamente", admin.getIdAdmin());
        return mapToResponse(admin, null);
    }

    public AdminResponseDTO crearAdmin(AdminRequestDTO dto) {
        log.info("Creando admin para usuario id: {}", dto.getIdUsuario());

        UsuarioDTO usuario;
        try {
            usuario = usuarioFeignClient.obtenerUsuarioPorId(dto.getIdUsuario());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Usuario con id " + dto.getIdUsuario() + " no encontrado");
        } catch (FeignException e) {
            throw new RuntimeException("No se puede conectar con usuario-service: " + e.getMessage());
        }

        if (adminRepository.existsByIdUsuario(dto.getIdUsuario())) {
            throw new BusinessException("El usuario con id " + dto.getIdUsuario() + " ya es administrador");
        }

        Admin admin = Admin.builder()
                .idUsuario(dto.getIdUsuario())
                .credencial(passwordEncoder.encode(dto.getCredencial()))
                .nivelAcceso(dto.getNivelAcceso())
                .fechaAsignacion(dto.getFechaAsignacion())
                .activo(true)
                .build();

        Admin guardado = adminRepository.save(admin);
        log.info("Admin creado con id: {}", guardado.getIdAdmin());

        return mapToResponse(guardado, usuario.getNombrePantalla());
    }

    public AdminResponseDTO actualizarNivelAcceso(Long id, NivelAcceso nuevoNivel) {
        log.info("Actualizando nivel de acceso del admin id: {} a {}", id, nuevoNivel);

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no encontrado con id: " + id));

        if (!admin.getActivo()) {
            throw new BusinessException("No se puede modificar un admin inactivo");
        }

        admin.setNivelAcceso(nuevoNivel);
        Admin actualizado = adminRepository.save(admin);

        log.info("Nivel de acceso actualizado correctamente para admin id: {}", id);
        return mapToResponse(actualizado, null);
    }

    public AdminResponseDTO desactivarAdmin(Long id) {
        log.info("Desactivando admin id: {}", id);

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no encontrado con id: " + id));

        if (!admin.getActivo()) {
            throw new BusinessException("El admin ya se encuentra inactivo");
        }

        admin.setActivo(false);
        Admin actualizado = adminRepository.save(admin);

        log.info("Admin id {} desactivado correctamente", id);
        return mapToResponse(actualizado, null);
    }

    public void eliminarAdmin(Long id) {
        log.info("Eliminando admin id: {}", id);

        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin no encontrado con id: " + id);
        }

        adminRepository.deleteById(id);
        log.info("Admin id {} eliminado correctamente", id);
    }

    private AdminResponseDTO mapToResponse(Admin admin, String nombreUsuario) {
        return AdminResponseDTO.builder()
                .idAdmin(admin.getIdAdmin())
                .idUsuario(admin.getIdUsuario())
                .nivelAcceso(admin.getNivelAcceso())
                .fechaAsignacion(admin.getFechaAsignacion())
                .activo(admin.getActivo())
                .nombreUsuario(nombreUsuario)
                .build();
    }
}