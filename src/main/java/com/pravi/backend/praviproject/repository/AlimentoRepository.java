package com.pravi.backend.praviproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Usuario;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    List<Alimento> findByUsuario(Usuario usuario);

    Optional<Alimento> findByIdAlimentoAndUsuario(Long idAlimento, Usuario usuario);

}
