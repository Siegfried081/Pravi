package com.pravi.backend.praviproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.DTO.UsuarioMapper;
import com.pravi.backend.praviproject.DTO.UsuarioRequestDTO;
import com.pravi.backend.praviproject.DTO.UsuarioResponseDTO;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.UsuarioRepository;

@Service
public class UsuarioService {
 private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario = repository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(UsuarioMapper::toDTO)
                .orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public UsuarioResponseDTO getMe(Usuario usuario) {
        return UsuarioMapper.toDTO(usuario);
    }

}
