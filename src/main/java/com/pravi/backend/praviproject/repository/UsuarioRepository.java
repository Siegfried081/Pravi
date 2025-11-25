package com.pravi.backend.praviproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pravi.backend.praviproject.entity.Familia;
import com.pravi.backend.praviproject.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    // usado no AuthService.register()
    boolean existsByEmail(String email);

    // conta quantos usuários existem em uma família
    long countByFamilia(Familia familia);
}