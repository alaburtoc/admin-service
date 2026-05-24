package com.gameup.admin_service.controller;

import com.gameup.admin_service.dto.AdminLoginRequest;
import com.gameup.admin_service.dto.AdminRequestDTO;
import com.gameup.admin_service.dto.AdminResponseDTO;
import com.gameup.admin_service.model.NivelAcceso;
import com.gameup.admin_service.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ── Libre: no requiere login ──────────────────────────────────────────

    @PostMapping
    public ResponseEntity<AdminResponseDTO> crearAdmin(
            @Valid @RequestBody AdminRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.crearAdmin(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AdminLoginRequest request) {
        AdminResponseDTO admin = adminService.autenticar(request);
        return ResponseEntity.ok("Login exitoso. Bienvenido admin id: " + admin.getIdAdmin()
                + " | Nivel: " + admin.getNivelAcceso());
    }

    // ── Protegidos: primero hacer POST /login ─────────────────────────────

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> listarAdmins() {
        return ResponseEntity.ok(adminService.obtenerAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.obtenerAdminPorId(id));
    }

    @GetMapping("/nivel/{nivelAcceso}")
    public ResponseEntity<List<AdminResponseDTO>> obtenerPorNivel(
            @PathVariable NivelAcceso nivelAcceso) {
        return ResponseEntity.ok(adminService.obtenerPorNivelAcceso(nivelAcceso));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<AdminResponseDTO>> obtenerActivos() {
        return ResponseEntity.ok(adminService.obtenerActivos());
    }

    @PatchMapping("/{id}/nivel")
    public ResponseEntity<AdminResponseDTO> actualizarNivel(
            @PathVariable Long id,
            @RequestParam NivelAcceso nuevoNivel) {
        return ResponseEntity.ok(adminService.actualizarNivelAcceso(id, nuevoNivel));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<AdminResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.desactivarAdmin(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        adminService.eliminarAdmin(id);
        return ResponseEntity.noContent().build();
    }
}