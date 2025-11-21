package com.pravi.backend.praviproject.DTO;

import com.pravi.backend.praviproject.entity.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        return usuario;
    }

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getIdUsuario(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
