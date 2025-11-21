package com.pravi.backend.praviproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.DTO.UsuarioLoginDTO;
import com.pravi.backend.praviproject.DTO.UsuarioRegisterDTO;
import com.pravi.backend.praviproject.DTO.UsuarioResponseDTO;
import com.pravi.backend.praviproject.Utils.PasswordEncoder;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.UsuarioRepository;

@Service
public class AuthService {
 @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO register(UsuarioRegisterDTO dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(PasswordEncoder.encode(dto.senha()));

        usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public UsuarioResponseDTO login(UsuarioLoginDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!PasswordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
