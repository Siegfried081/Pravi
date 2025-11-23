package com.pravi.backend.praviproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pravi.backend.praviproject.DTO.FamiliaCreateDTO;
import com.pravi.backend.praviproject.DTO.FamiliaJoinDTO;
import com.pravi.backend.praviproject.DTO.FamiliaMapper;
import com.pravi.backend.praviproject.DTO.FamiliaRequestDTO;
import com.pravi.backend.praviproject.DTO.FamiliaResponseDTO;
import com.pravi.backend.praviproject.entity.Familia;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.FamiliaRepository;
import com.pravi.backend.praviproject.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamiliaService {
    private final FamiliaRepository familiaRepository;
    private final UsuarioRepository usuarioRepository;

    // Gera códigos como: FAM-7G92KD
    private String gerarCodigoAcesso() {
        return "FAM-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public FamiliaResponseDTO criarFamilia(FamiliaCreateDTO dto, Usuario usuarioLogado) {

        if (usuarioLogado.getFamilia() != null) {
            throw new RuntimeException("Você já pertence a uma família.");
        }

        // criar família
        Familia familia = new Familia();
        familia.setNomeFamilia(dto.nomeFamilia());
        familia.setCodigoAcesso(gerarCodigoAcesso());
        familia.setUsuarios(new ArrayList<>());

        // Reanexar o usuário ao contexto JPA
        usuarioLogado = usuarioRepository.findById(usuarioLogado.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // vinculação correta
        usuarioLogado.setFamilia(familia);
        familia.getUsuarios().add(usuarioLogado);

        // agora sim: merge, não persist
        familia = familiaRepository.saveAndFlush(familia);

        return FamiliaMapper.toDTO(familia);
    }

    public FamiliaResponseDTO salvar(FamiliaRequestDTO dto) {
        Familia familia = FamiliaMapper.toEntity(dto);
        familia = familiaRepository.save(familia);
        return FamiliaMapper.toDTO(familia);
    }

    public List<FamiliaResponseDTO> listar() {
        return familiaRepository.findAll()
                .stream()
                .map(FamiliaMapper::toDTO)
                .toList();
    }

    public FamiliaResponseDTO buscarPorId(Long id) {
        return familiaRepository.findById(id)
                .map(FamiliaMapper::toDTO)
                .orElse(null);
    }

    public void deletar(Long id) {
        familiaRepository.deleteById(id);
    }

    public FamiliaResponseDTO entrarFamilia(FamiliaJoinDTO dto, Usuario usuario) {
        Familia familia = familiaRepository.findByCodigoAcesso(dto.codigoAcesso())
                .orElseThrow(() -> new RuntimeException("Código de acesso inválido"));

        usuario.setFamilia(familia);
        usuarioRepository.save(usuario);

        return FamiliaMapper.toDTO(familia);
    }    

    @Transactional
    public void sairFamilia(Usuario usuarioLogado) {

        // 1) Reanexa o usuário ao contexto
        Usuario usuario = usuarioRepository.findById(usuarioLogado.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Familia familia = usuario.getFamilia();
        if (familia == null) {
            throw new RuntimeException("Você não pertence a nenhuma família.");
        }

        // 2) Reanexa a família ao contexto JPA
        familia = familiaRepository.findById(familia.getIdFamilia())
                .orElseThrow(() -> new RuntimeException("Família não encontrada."));

        // 3) Remove o usuário da família (com coleção já inicializada)
        familia.getUsuarios().remove(usuario);

        // 4) Desvincula o usuário da família
        usuario.setFamilia(null);
        usuarioRepository.save(usuario);

        // 5) Se a família ficar vazia -> deletar
        if (familia.getUsuarios().isEmpty()) {
            familiaRepository.delete(familia);
        } else {
            familiaRepository.save(familia);
        }
    }

    public FamiliaResponseDTO minhaFamilia(Usuario usuario) {

        if (usuario.getFamilia() == null) {
            throw new RuntimeException("Você não pertence a nenhuma família.");
        }

        Familia familia = familiaRepository.findById(usuario.getFamilia().getIdFamilia())
                .orElseThrow(() -> new RuntimeException("Família não encontrada."));

        // FORÇA INICIALIZAÇÃO DA LISTA (resolve LazyInitializationException)
        familia.getUsuarios().size();

        return FamiliaMapper.toDTO(familia);
    }
}
