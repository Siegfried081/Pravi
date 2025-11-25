package com.pravi.backend.praviproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.security.UsuarioDetails;
import com.pravi.backend.praviproject.service.AlimentoAlertService;
import com.pravi.backend.praviproject.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlimentoAlertService alertService;
    private final EmailService emailService;

    private Usuario getUsuarioLogado() {
        return ((UsuarioDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsuario();
    }

    @GetMapping("/alimentos")
    public ResponseEntity<List<Alimento>> alimentosVencendo() {
        Usuario usuario = getUsuarioLogado();
        return ResponseEntity.ok(alertService.buscarAlimentosPrestesAVencer(usuario));
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarEmailAlerta() {

        Usuario usuario = getUsuarioLogado();
        List<Alimento> vencendo = alertService.buscarAlimentosPrestesAVencer(usuario);

        if (vencendo.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum alimento prestes a vencer.");
        }

        StringBuilder mensagem = new StringBuilder("Alimentos prestes a vencer:\n\n");

        vencendo.forEach(a -> mensagem.append(
                "- " + a.getNome() + " (vence em: " + a.getDataValidade() + ")\n"
        ));

        emailService.enviarEmail(usuario.getEmail(),
                "Aviso: alimentos prestes a vencer",
                mensagem.toString());

        return ResponseEntity.ok("E-mail enviado.");
    }
}
