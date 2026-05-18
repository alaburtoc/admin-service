package com.gameup.admin_service.client;

import com.gameup.admin_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "${usuario.service.url}")
public interface UsuarioFeignClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}