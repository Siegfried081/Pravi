package com.pravi.backend.praviproject.DTO;

import com.pravi.backend.praviproject.entity.Alimento;

public class AlimentoMapper {

    public static Alimento toEntity(AlimentoRequestDTO dto) {
        Alimento alimento = new Alimento();
        alimento.setNome(dto.nome());
        return alimento;
    }

    public static AlimentoResponseDTO toDTO(Alimento alimento) {
        return new AlimentoResponseDTO(
            alimento.getIdAlimento(),
            alimento.getNome()
        );
    }
}
