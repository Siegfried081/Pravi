package com.pravi.backend.praviproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.pravi.backend.praviproject.DTO.AlimentoRequestDTO;
import com.pravi.backend.praviproject.DTO.AlimentoResponseDTO;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.service.AlimentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alimentos")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    @PostMapping
    public ResponseEntity<AlimentoResponseDTO> criar(@RequestBody AlimentoRequestDTO dto) {

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        AlimentoResponseDTO response = alimentoService.cadastrar(dto, usuarioLogado);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AlimentoResponseDTO>> listar() {

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(alimentoService.listar(usuarioLogado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoResponseDTO> buscarPorId(@PathVariable Long id) {

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(alimentoService.buscarPorId(id, usuarioLogado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        alimentoService.deletar(id, usuarioLogado);

        return ResponseEntity.noContent().build();
    }
}
