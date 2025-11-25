package com.pravi.backend.praviproject.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvisoVencimentoScheduler {

    private final UsuarioRepository usuarioRepository;
    private final DashboardService dashboardService;

    /**
     * Cron do Spring: segundo minuto hora dia-mês mês dia-semana
     * 0 0 20 * * *  -> todos os dias às 20:00
     */
    //@Scheduled(cron = "0 0 20 * * *")
    @Scheduled(cron = "*/10 * * * * *", zone = "America/Sao_Paulo")
    public void enviarAvisosDiarios() {

        log.info("[Scheduler] Iniciando verificação de alimentos a 7 dias de vencer...");

        List<Usuario> usuarios = usuarioRepository.findAll();
        int totalComEmail = 0;

        for (Usuario usuario : usuarios) {
            try {
                // Se o usuário não tiver email, ignora
                if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
                    continue;
                }

                // Aqui reaproveitamos TODA a lógica que já funciona hoje
                // (a mesma do botão na Dashboard)
                dashboardService.enviarAvisoAlimentos(usuario);
                totalComEmail++;

            } catch (Exception e) {
                log.warn("[Scheduler] Erro ao enviar aviso para usuário id={} email={}: {}",
                        usuario.getIdUsuario(),
                        usuario.getEmail(),
                        e.getMessage());
            }
        }

        log.info("[Scheduler] Execução concluída. Processados {} usuários.", totalComEmail);
    }
}
