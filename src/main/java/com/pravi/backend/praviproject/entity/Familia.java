package com.pravi.backend.praviproject.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "familia")
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFamilia;

    @Column(nullable = false, length = 100)
    private String nomeFamilia;

    @Column(nullable = false, unique = true, length = 20)
    private String codigoAcesso;

    @OneToMany(mappedBy = "familia")
    private List<Usuario> usuarios = new ArrayList<>();
}
