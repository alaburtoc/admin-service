package com.gameup.admin_service.securty;

import com.gameup.admin_service.model.Admin;
import com.gameup.admin_service.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String idAdmin) throws UsernameNotFoundException {

        Admin admin = adminRepository.findById(Long.parseLong(idAdmin))
                .orElseThrow(() -> new UsernameNotFoundException("Admin no encontrado con id: " + idAdmin));


        return new org.springframework.security.core.userdetails.User(
                String.valueOf(admin.getIdAdmin()),
                admin.getCredencial(),
                Collections.emptyList()
        );
    }
}