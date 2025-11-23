package com.pravi.backend.praviproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pravi.backend.praviproject.DTO.AlimentoRequestDTO;
import com.pravi.backend.praviproject.DTO.AlimentoResponseDTO;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.security.UsuarioDetails;
import com.pravi.backend.praviproject.service.AlimentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alimentos")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    @PostMapping
    public ResponseEntity<AlimentoResponseDTO> criar(@RequestBody AlimentoRequestDTO dto) {

        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

        Usuario usuarioLogado = usuarioDetails.getUsuario();

        AlimentoResponseDTO response = alimentoService.cadastrar(dto, usuarioLogado);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AlimentoResponseDTO>> listar() {

        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

        Usuario usuarioLogado = usuarioDetails.getUsuario();

        return ResponseEntity.ok(alimentoService.listar(usuarioLogado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoResponseDTO> buscarPorId(@PathVariable Long id) {

        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

        Usuario usuarioLogado = usuarioDetails.getUsuario();

        return ResponseEntity.ok(alimentoService.buscarPorId(id, usuarioLogado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuarioLogado = usuarioDetails.getUsuario();

        alimentoService.deletar(id, usuarioLogado);

        return ResponseEntity.noContent().build();
    }

}
