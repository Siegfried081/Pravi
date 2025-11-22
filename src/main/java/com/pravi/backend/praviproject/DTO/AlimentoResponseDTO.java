package com.pravi.backend.praviproject.DTO;

import java.time.LocalDate;

import com.pravi.backend.praviproject.entity.enums.Categoria;

public record AlimentoResponseDTO(
    Long idAlimento, 
    String nome, 
    LocalDate dataValidade, 
    LocalDate dataCompra, 
    Categoria categoria, 
    String tipo, 
    int quantidade) {

}
