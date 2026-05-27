package com.gameup.admin_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminLoginRequest {

    @NotNull(message = "El id del admin es obligatorio")
    private Long idAdmin;

    @NotBlank(message = "La credencial no puede estar vacía")
    private String credencial;
}