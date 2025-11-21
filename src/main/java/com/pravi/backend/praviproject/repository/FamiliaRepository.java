package com.pravi.backend.praviproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pravi.backend.praviproject.entity.Familia;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    
}
