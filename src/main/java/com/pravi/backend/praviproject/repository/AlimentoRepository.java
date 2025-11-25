package com.pravi.backend.praviproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Familia;
import com.pravi.backend.praviproject.entity.Usuario;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    List<Alimento> findByUsuario(Usuario usuario);

    Optional<Alimento> findByIdAlimentoAndUsuario(Long idAlimento, Usuario usuario);

    long countByUsuario(Usuario usuario);

    long countByUsuario_Familia(Familia familia);

    List<Alimento> findByUsuarioIn(List<Usuario> usuarios);
}