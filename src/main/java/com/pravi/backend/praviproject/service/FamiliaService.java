package com.pravi.backend.praviproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.DTO.FamiliaMapper;
import com.pravi.backend.praviproject.DTO.FamiliaRequestDTO;
import com.pravi.backend.praviproject.DTO.FamiliaResponseDTO;
import com.pravi.backend.praviproject.entity.Familia;
import com.pravi.backend.praviproject.repository.FamiliaRepository;

@Service
public class FamiliaService {
     private final FamiliaRepository repository;

    public FamiliaService(FamiliaRepository repository) {
        this.repository = repository;
    }

    public FamiliaResponseDTO salvar(FamiliaRequestDTO dto) {
        Familia familia = FamiliaMapper.toEntity(dto);
        familia = repository.save(familia);
        return FamiliaMapper.toDTO(familia);
    }

    public List<FamiliaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(FamiliaMapper::toDTO)
                .toList();
    }

    public FamiliaResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(FamiliaMapper::toDTO)
                .orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
