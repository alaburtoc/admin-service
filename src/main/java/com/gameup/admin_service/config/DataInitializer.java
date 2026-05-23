package com.gameup.admin_service.config;

import com.gameup.admin_service.model.Admin;
import com.gameup.admin_service.model.NivelAcceso;
import com.gameup.admin_service.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si la tabla ya tiene datos para no duplicar
        if (adminRepository.count() > 0) {
            log.info(">>> Admins ya cargados. Se omite la inicialización.");
            return;
        }

        // Admin 1 - nivel SUPER_ADMIN, asociado al usuario con id 1
        Admin admin1 = new Admin();
        admin1.setIdUsuario(1L);
        admin1.setCredencial(passwordEncoder.encode("superadmin123"));
        admin1.setNivelAcceso(NivelAcceso.SUPER_ADMIN);
        admin1.setFechaAsignacion(LocalDate.of(2024, 1, 15));
        admin1.setActivo(true);

        // Admin 2 - nivel ALTO, asociado al usuario con id 2
        Admin admin2 = new Admin();
        admin2.setIdUsuario(2L);
        admin2.setCredencial(passwordEncoder.encode("adminAlto456"));
        admin2.setNivelAcceso(NivelAcceso.ALTO);
        admin2.setFechaAsignacion(LocalDate.of(2024, 3, 10));
        admin2.setActivo(true);

        // Admin 3 - nivel MEDIO, asociado al usuario con id 3
        Admin admin3 = new Admin();
        admin3.setIdUsuario(3L);
        admin3.setCredencial(passwordEncoder.encode("adminMedio789"));
        admin3.setNivelAcceso(NivelAcceso.MEDIO);
        admin3.setFechaAsignacion(LocalDate.of(2025, 6, 20));
        admin3.setActivo(true);

        // Guardar todos en la base de datos
        adminRepository.save(admin1);
        adminRepository.save(admin2);
        adminRepository.save(admin3);

        log.info(">>> 3 admins cargados OK.");
    }
}