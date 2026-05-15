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

    @GetMapping
    public List<Admin> listarAdmins() {
        return adminService.obtenerAdmins();
    }

    @PostMapping
    public AdminResponseDTO crearAdmin(@RequestBody AdminRequestDTO dto) {
        return adminService.crearAdmin(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarAdmin(@PathVariable Long id) {
        adminService.eliminarAdmin(id);
    }
}
