package com.gameup.admin_service.dto;

import com.gameup.admin_service.model.NivelAcceso;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {
    private Long idAdmin;
    private Long idUsuario;
    private String credencial;
    private NivelAcceso nivelAcceso;
    private LocalDate fechaAsignacion;
    private Boolean activo;

    // Dato enriquecido desde usuario-service
    private String nombreUsuario;
}