package com.gameup.admin_service.service;

import com.gameup.admin_service.dto.AdminRequestDTO;
import com.gameup.admin_service.dto.AdminResponseDTO;
import com.gameup.admin_service.exception.ResourceNotFoundException;
import com.gameup.admin_service.model.Admin;
import com.gameup.admin_service.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // GET ALL
    public List<Admin> obtenerAdmins() {
        return adminRepository.findAll();
    }

    // GET BY ID
    public Admin obtenerAdminPorId(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin no encontrado con id: " + id));
    }

    // POST
    public AdminResponseDTO crearAdmin(AdminRequestDTO dto) {

        Admin admin = new Admin();

        admin.setIdUsuario(dto.getIdUsuario());
        admin.setCredencial(dto.getCredencial());
        admin.setNivelAcceso(dto.getNivelAcceso());
        admin.setFechaAsignacion(dto.getFechaAsignacion());

        Admin adminGuardado = adminRepository.save(admin);

        return new AdminResponseDTO(
                adminGuardado.getIdAdmin(),
                adminGuardado.getIdUsuario(),
                adminGuardado.getCredencial(),
                adminGuardado.getNivelAcceso(),
                adminGuardado.getFechaAsignacion()
        );
    }

    // DELETE
    public void eliminarAdmin(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin no encontrado con id: " + id));

        adminRepository.delete(admin);
    }
}
