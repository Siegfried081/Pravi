package com.pravi.backend.praviproject.controller;

import com.pravi.backend.praviproject.DTO.DashboardResumoDTO;
import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Familia;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.AlimentoRepository;
import com.pravi.backend.praviproject.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  private final UsuarioRepository usuarioRepository;
  private final AlimentoRepository alimentoRepository;

  public DashboardController(UsuarioRepository usuarioRepository,
                             AlimentoRepository alimentoRepository) {
    this.usuarioRepository = usuarioRepository;
    this.alimentoRepository = alimentoRepository;
  }

  @GetMapping("/resumo")
  public ResponseEntity<DashboardResumoDTO> resumo(Authentication authentication) {
    // e-mail do usuário autenticado (do token JWT)
    String email = authentication.getName();

    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário autenticado não encontrado"
            ));

    Familia familia = usuario.getFamilia();

    // ---------- membros da família ----------
    int qtdMembros = 0;
    if (familia != null) {
      qtdMembros = (int) usuarioRepository.countByFamilia(familia);
    }

    // ---------- alimentos do usuário ----------
    int qtdAlimentosUsuario = (int) alimentoRepository.countByUsuario(usuario);

    // ---------- alimentos da família ----------
    int qtdAlimentosFamilia = 0;
    if (familia != null) {
      qtdAlimentosFamilia = (int) alimentoRepository.countByUsuario_Familia(familia);
    }

    // Nome / id / data da família
    Long idFamilia = null;
    String nomeFamilia = null;
    String criadaEm = null; // se tiver campo, converte aqui

    if (familia != null) {
      idFamilia = familia.getIdFamilia();
      nomeFamilia = familia.getNomeFamilia();
      // exemplo, se tiver getDataCriacao():
      // if (familia.getDataCriacao() != null) {
      //     criadaEm = familia.getDataCriacao().toString();
      // }
    }

    DashboardResumoDTO dto = new DashboardResumoDTO(
            usuario.getNome(),
            usuario.getEmail(),
            idFamilia,
            nomeFamilia,
            qtdMembros,
            qtdAlimentosUsuario,
            qtdAlimentosFamilia,
            criadaEm
    );

    return ResponseEntity.ok(dto);
  }
}