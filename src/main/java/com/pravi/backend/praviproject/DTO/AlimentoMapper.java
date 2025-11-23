package com.pravi.backend.praviproject.DTO;

import com.pravi.backend.praviproject.entity.Alimento;

public class AlimentoMapper {

    public static Alimento toEntity(AlimentoRequestDTO dto) {
        Alimento alimento = new Alimento();
        alimento.setNome(dto.nome());
        alimento.setDataValidade(dto.dataValidade());
        alimento.setCategoria(dto.categoria());
        alimento.setQuantidade(dto.quantidade());
        return alimento;
    }

    public static AlimentoResponseDTO toDTO(Alimento alimento) {
        return new AlimentoResponseDTO(
            alimento.getIdAlimento(),
            alimento.getNome(),
            alimento.getDataValidade(),
            alimento.getDataCompra(),
            alimento.getCategoria(),
            alimento.getQuantidade()
        );
    }
}
