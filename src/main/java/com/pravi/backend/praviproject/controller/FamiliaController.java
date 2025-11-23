package com.pravi.backend.praviproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pravi.backend.praviproject.DTO.FamiliaCreateDTO;
import com.pravi.backend.praviproject.DTO.FamiliaJoinDTO;
import com.pravi.backend.praviproject.DTO.FamiliaResponseDTO;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.security.UsuarioDetails;
import com.pravi.backend.praviproject.service.FamiliaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/familias")
@RequiredArgsConstructor
public class FamiliaController {

    private final FamiliaService familiaService;

    private Usuario getUsuarioLogado() {
        UsuarioDetails ud = (UsuarioDetails)
                SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ud.getUsuario();
    }

    @PostMapping("/criar")
    public ResponseEntity<FamiliaResponseDTO> criar(@RequestBody FamiliaCreateDTO dto) {
        return ResponseEntity.ok(familiaService.criarFamilia(dto, getUsuarioLogado()));
    }

    @PostMapping("/entrar")
    public ResponseEntity<FamiliaResponseDTO> entrar(@RequestBody FamiliaJoinDTO dto) {
        return ResponseEntity.ok(familiaService.entrarFamilia(dto, getUsuarioLogado()));
    }

    @PostMapping("/sair")
    public ResponseEntity<Void> sair() {
        familiaService.sairFamilia(getUsuarioLogado());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/minha")
    public ResponseEntity<FamiliaResponseDTO> minha() {
        return ResponseEntity.ok(familiaService.minhaFamilia(getUsuarioLogado()));
    }
}
