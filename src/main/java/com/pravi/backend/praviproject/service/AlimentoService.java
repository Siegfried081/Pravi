package com.pravi.backend.praviproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.DTO.AlimentoMapper;
import com.pravi.backend.praviproject.DTO.AlimentoRequestDTO;
import com.pravi.backend.praviproject.DTO.AlimentoResponseDTO;
import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.repository.AlimentoRepository;


@Service
public class AlimentoService {

    private final AlimentoRepository repository;

    public AlimentoService(AlimentoRepository repository) {
        this.repository = repository;
    }

    public AlimentoResponseDTO salvar(AlimentoRequestDTO dto) {
        Alimento alimento = AlimentoMapper.toEntity(dto);
        alimento = repository.save(alimento);
        return AlimentoMapper.toDTO(alimento);
    }

    public List<AlimentoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(AlimentoMapper::toDTO)
                .toList();
    }

    public AlimentoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(AlimentoMapper::toDTO)
                .orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
