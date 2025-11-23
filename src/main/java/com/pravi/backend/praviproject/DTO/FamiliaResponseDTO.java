package com.pravi.backend.praviproject.DTO;

import java.util.List;

public record FamiliaResponseDTO(
        Long idFamilia,
        String nomeFamilia,
        String codigoAcesso,
        List<String> membros
) {}

