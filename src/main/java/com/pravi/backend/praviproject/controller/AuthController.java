package com.pravi.backend.praviproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pravi.backend.praviproject.DTO.UsuarioLoginDTO;
import com.pravi.backend.praviproject.DTO.UsuarioRegisterDTO;
import com.pravi.backend.praviproject.DTO.UsuarioResponseDTO;
import com.pravi.backend.praviproject.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioRegisterDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(token);
    }
}
