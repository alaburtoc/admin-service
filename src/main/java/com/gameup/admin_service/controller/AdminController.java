package com.gameup.admin_service.controller;

import com.gameup.admin_service.dto.AdminRequestDTO;
import com.gameup.admin_service.dto.AdminResponseDTO;
import com.gameup.admin_service.model.Admin;
import com.gameup.admin_service.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // GET ALL
    @GetMapping
    public List<Admin> listarAdmins() {
        return adminService.obtenerAdmins();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Admin obtenerAdminPorId(@PathVariable Long id) {
        return adminService.obtenerAdminPorId(id);
    }

    // POST
    @PostMapping
    public AdminResponseDTO crearAdmin(@RequestBody AdminRequestDTO dto) {
        return adminService.crearAdmin(dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminarAdmin(@PathVariable Long id) {
        adminService.eliminarAdmin(id);
    }
}
