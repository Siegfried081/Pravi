package com.pravi.backend.praviproject.DTO;

import java.time.LocalDate;

import com.pravi.backend.praviproject.entity.enums.Categoria;

public record AlimentoRequestDTO(
    String nome, 
    LocalDate dataValidade,
    Categoria categoria, 
    int quantidade) {
}
