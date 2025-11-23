package com.pravi.backend.praviproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pravi.backend.praviproject.entity.Familia;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    Optional<Familia> findByCodigoAcesso(String codigoAcesso);
}
