package com.pravi.backend.praviproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.AlimentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AlimentoRepository alimentoRepository;
    private final EmailService emailService;

    /**
     * Envia aviso por e-mail sobre alimentos que vencem em até 7 dias
     */
    @Transactional
    public void enviarAvisoAlimentos(Usuario usuarioLogado) {

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(7);

        // alimentos do próprio usuário
        List<Alimento> meus = alimentoRepository.findByUsuario(usuarioLogado)
                .stream()
                .filter(a -> !a.getDataValidade().isAfter(limite))
                .toList();

        // alimentos da família
        List<Alimento> familia = new ArrayList<>();

        if (usuarioLogado.getFamilia() != null) {
            List<Usuario> membros = usuarioLogado.getFamilia().getUsuarios();

            familia = alimentoRepository.findByUsuarioIn(membros)
                    .stream()
                    .filter(a -> !a.getDataValidade().isAfter(limite))
                    .toList();
        }

        // junta tudo
        List<Alimento> total = new ArrayList<>();
        total.addAll(meus);
        total.addAll(familia);

        if (total.isEmpty()) {
            return; // nada para enviar
        }

        StringBuilder texto = new StringBuilder("Alimentos próximos do vencimento:\n\n");

        for (Alimento a : total) {
            texto.append("- ")
                 .append(a.getNome())
                 .append(" | Vence em: ").append(a.getDataValidade())
                 .append(" | Quant.: ").append(a.getQuantidade())
                 .append("\n");
        }

        emailService.enviarEmail(
            usuarioLogado.getEmail(),
            "Aviso: Alimentos próximos do vencimento",
            texto.toString()
        );
    }
}
