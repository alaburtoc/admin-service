package com.gameup.admin_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Long idAdmin;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String credencial;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acceso", nullable = false, length = 20)
    private NivelAcceso nivelAcceso;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @Column(nullable = false)
    private Boolean activo;

    @PrePersist
    protected void onCreate() {
        if (this.fechaAsignacion == null) {
            this.fechaAsignacion = LocalDate.now();
        }
        if (this.activo == null) {
            this.activo = true;
        }
    }
}