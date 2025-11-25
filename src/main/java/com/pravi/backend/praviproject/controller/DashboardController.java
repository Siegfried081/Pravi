package com.pravi.backend.praviproject.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pravi.backend.praviproject.DTO.DashboardResumoDTO;
import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Familia;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.AlimentoRepository;
import com.pravi.backend.praviproject.repository.UsuarioRepository;
import com.pravi.backend.praviproject.security.UsuarioDetails;
import com.pravi.backend.praviproject.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final AlimentoRepository alimentoRepository;
    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoDTO> resumo(Authentication authentication) {

        // e-mail extraído do JWT
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário autenticado não encontrado"
                ));

        Familia familia = usuario.getFamilia();

        // ---------- membros da família ----------
        int qtdMembros = (familia != null)
                ? (int) usuarioRepository.countByFamilia(familia)
                : 0;

        // ---------- alimentos do usuário ----------
        int qtdAlimentosUsuario = (int) alimentoRepository.countByUsuario(usuario);

        // ---------- alimentos da família ----------
        int qtdAlimentosFamilia = (familia != null)
                ? (int) alimentoRepository.countByUsuario_Familia(familia)
                : 0;

        // ---------- dados da família ----------
        Long idFamilia = (familia != null) ? familia.getIdFamilia() : null;
        String nomeFamilia = (familia != null) ? familia.getNomeFamilia() : null;
        String criadaEm = null; // Caso você adicione data de criação futuramente

        // ---------- alimentos prestes a vencer ----------
        boolean temAlimentosVencendo = verificarAlimentosVencendo(usuario, familia);

        int qtdVencidosUsuario = alimentoRepository.findByUsuario(usuario)
        .stream()
        .filter(a -> a.getDataValidade().isBefore(LocalDate.now()))
        .toList()
        .size();

        int qtdVencidosFamilia = 0;
        if (familia != null) {
            qtdVencidosFamilia = alimentoRepository.findByUsuarioIn(familia.getUsuarios())
            .stream()
            .filter(a -> a.getDataValidade().isBefore(LocalDate.now()))
            .toList()
            .size();
        }


        DashboardResumoDTO dto = new DashboardResumoDTO(
            usuario.getNome(),
            usuario.getEmail(),
            idFamilia,
            nomeFamilia,
            qtdMembros,
            qtdAlimentosUsuario,
            qtdAlimentosFamilia,
            criadaEm,
            temAlimentosVencendo,
            qtdVencidosUsuario,      // NOVO
            qtdVencidosFamilia       // NOVO
        );


        return ResponseEntity.ok(dto);
    }

    /**
     * Verifica se há alimentos do usuário OU da família que vencem nos próximos 7 dias.
     */
    private boolean verificarAlimentosVencendo(Usuario usuario, Familia familia) {

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(7);

        // alimentos do usuário
        List<Alimento> alimentosUsuario = alimentoRepository.findByUsuario(usuario);

        boolean usuarioTem = alimentosUsuario.stream()
                .anyMatch(a -> 
                        !a.getDataValidade().isBefore(hoje) &&
                        a.getDataValidade().isBefore(limite)
                );

        if (usuarioTem) return true;

        // alimentos da família
        if (familia != null) {
            List<Usuario> membros = familia.getUsuarios();
            List<Alimento> alimentosFamilia = alimentoRepository.findByUsuarioIn(membros);

            return alimentosFamilia.stream()
                    .anyMatch(a -> 
                            !a.getDataValidade().isBefore(hoje) &&
                            a.getDataValidade().isBefore(limite)
                    );
        }

        return false;
    }

    // ---------------------------------------------------------------------

    @PostMapping("/enviar-aviso")
    public ResponseEntity<Void> enviarAviso() {

        UsuarioDetails ud = (UsuarioDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = ud.getUsuario();

        dashboardService.enviarAvisoAlimentos(usuario);

        return ResponseEntity.ok().build();
    }
}
