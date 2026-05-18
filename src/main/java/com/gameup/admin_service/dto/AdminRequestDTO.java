package com.gameup.admin_service.dto;

import com.gameup.admin_service.model.NivelAcceso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {

    @NotNull(message = "El id_usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "La credencial no puede estar vacía")
    @Size(max = 100, message = "La credencial no puede superar los 100 caracteres")
    private String credencial;

    @NotNull(message = "El nivel de acceso es obligatorio")
    private NivelAcceso nivelAcceso;

    private LocalDate fechaAsignacion;
}