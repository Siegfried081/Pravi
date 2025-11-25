package com.pravi.backend.praviproject.DTO;

public record UsuarioResponseDTO(
    Long idUsuario,
    String nome,
    String email,
    Long idFamilia,
    String nomeFamilia
) {}
