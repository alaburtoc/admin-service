package com.gameup.admin_service.dto;

import java.time.LocalDate;

public class AdminResponseDTO {

    private Long idAdmin;

    private Long idUsuario;

    private String credencial;

    private String nivelAcceso;

    private LocalDate fechaAsignacion;

    public AdminResponseDTO(Long idAdmin,
                            Long idUsuario,
                            String credencial,
                            String nivelAcceso,
                            LocalDate fechaAsignacion) {

        this.idAdmin = idAdmin;
        this.idUsuario = idUsuario;
        this.credencial = credencial;
        this.nivelAcceso = nivelAcceso;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getCredencial() {
        return credencial;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }
}