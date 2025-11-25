package com.pravi.backend.praviproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.AlimentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlimentoAlertService {

    private final AlimentoRepository alimentoRepository;

    public List<Alimento> buscarAlimentosPrestesAVencer(Usuario usuario) {

        LocalDate hoje = LocalDate.now();
        LocalDate daquiUmaSemana = hoje.plusDays(7);

        List<Usuario> usuariosParaVerificar = new ArrayList<>();

        // se tem família → pegar todos os membros
        if (usuario.getFamilia() != null) {
            usuariosParaVerificar.addAll(usuario.getFamilia().getUsuarios());
        } else {
            usuariosParaVerificar.add(usuario);
        }

        List<Alimento> todos = alimentoRepository.findByUsuarioIn(usuariosParaVerificar);

        // filtra alimentos vencendo em até 7 dias
        return todos.stream()
                .filter(a -> !a.getDataValidade().isBefore(hoje)
                          && !a.getDataValidade().isAfter(daquiUmaSemana))
                .toList();
    }
}
