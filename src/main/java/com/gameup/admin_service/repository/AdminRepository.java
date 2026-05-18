package com.gameup.admin_service.repository;

import com.gameup.admin_service.model.Admin;
import com.gameup.admin_service.model.NivelAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByNivelAcceso(NivelAcceso nivelAcceso);
    Optional<Admin> findByIdUsuario(Long idUsuario);
    List<Admin> findByActivo(Boolean activo);
    boolean existsByIdUsuario(Long idUsuario);
}