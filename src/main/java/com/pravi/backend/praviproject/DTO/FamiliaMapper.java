package com.pravi.backend.praviproject.DTO;

import com.pravi.backend.praviproject.entity.Familia;

public class FamiliaMapper {

    public static Familia toEntity(FamiliaRequestDTO dto) {
        Familia familia = new Familia();
        familia.setNomeFamilia(dto.nomeFamilia());
        return familia;
    }

    public static FamiliaResponseDTO toDTO(Familia familia) {
        return new FamiliaResponseDTO(
            familia.getIdFamilia(),
            familia.getNomeFamilia(),
            familia.getCodigoAcesso(),
            familia.getUsuarios()
                .stream()
                .map(usuario -> usuario.getNome())
                .toList()
        );
    }
}
