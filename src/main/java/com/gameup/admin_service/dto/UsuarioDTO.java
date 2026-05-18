package com.gameup.admin_service.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombrePantalla;
    private String email;
    private boolean cuentaBloqueada;
}