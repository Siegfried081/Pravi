package com.pravi.backend.praviproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pravi.backend.praviproject.DTO.UsuarioLoginDTO;
import com.pravi.backend.praviproject.DTO.UsuarioRegisterDTO;
import com.pravi.backend.praviproject.DTO.UsuarioResponseDTO;
import com.pravi.backend.praviproject.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioRegisterDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(token);
    }

}
