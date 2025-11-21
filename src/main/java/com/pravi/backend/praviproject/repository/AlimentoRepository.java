package com.pravi.backend.praviproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pravi.backend.praviproject.entity.Alimento;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
