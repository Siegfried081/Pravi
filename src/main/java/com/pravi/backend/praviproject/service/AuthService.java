package com.pravi.backend.praviproject.service;




import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.DTO.UsuarioLoginDTO;
import com.pravi.backend.praviproject.DTO.UsuarioRegisterDTO;
import com.pravi.backend.praviproject.DTO.UsuarioResponseDTO;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.UsuarioRepository;
import com.pravi.backend.praviproject.security.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UsuarioResponseDTO register(UsuarioRegisterDTO dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        // ao registrar, usuario não tem família ainda
        usuario.setFamilia(null);

        usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
            usuario.getIdUsuario(),
            usuario.getNome(),
            usuario.getEmail(),
            null,       
            null                
        );
    }


    public String login(UsuarioLoginDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }

        // Gere o JWT com id + email
        return jwtUtil.gerarToken(usuario.getIdUsuario(), usuario.getEmail());
    }
}
