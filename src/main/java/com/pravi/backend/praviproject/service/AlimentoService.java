package com.pravi.backend.praviproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pravi.backend.praviproject.DTO.AlimentoMapper;
import com.pravi.backend.praviproject.DTO.AlimentoRequestDTO;
import com.pravi.backend.praviproject.DTO.AlimentoResponseDTO;
import com.pravi.backend.praviproject.entity.Alimento;
import com.pravi.backend.praviproject.entity.Usuario;
import com.pravi.backend.praviproject.repository.AlimentoRepository;
import com.pravi.backend.praviproject.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final UsuarioRepository usuarioRepository;

    public AlimentoResponseDTO cadastrar(AlimentoRequestDTO dto, Usuario usuario) {

        Alimento alimento = AlimentoMapper.toEntity(dto);
        alimento.setDataCompra(LocalDate.now());
        alimento.setUsuario(usuario);

        alimentoRepository.save(alimento);

        return AlimentoMapper.toDTO(alimento);
    }

    public List<AlimentoResponseDTO> listar(Usuario usuario) {
        return alimentoRepository.findByUsuario(usuario)
                .stream()
                .map(AlimentoMapper::toDTO)
                .toList();
    }

    public AlimentoResponseDTO buscarPorId(Long id, Usuario usuario) {
        Alimento alimento = alimentoRepository.findByIdAlimentoAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado ou não pertence ao usuário."));

        return AlimentoMapper.toDTO(alimento);
    }

    public void deletar(Long id, Usuario usuario) {

        Alimento alimento = alimentoRepository.findByIdAlimentoAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado ou não pertence ao usuário."));

        alimentoRepository.delete(alimento);
    }

    public List<AlimentoResponseDTO> listarAlimentosDaFamilia(Usuario usuarioLogado) {

        // 1. Recarrega o usuário atualizado diretamente do banco
        Usuario usuario = usuarioRepository.findById(usuarioLogado.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // 2. Verifica família já atualizada
        if (usuario.getFamilia() == null) {
            throw new RuntimeException("Você não está em uma família.");
        }

        // 3. Obtém membros da família
        List<Usuario> membros = usuario.getFamilia().getUsuarios();

        // 4. Busca alimentos de todos os membros
        List<Alimento> alimentos = alimentoRepository.findByUsuarioIn(membros);

        return alimentos.stream()
                .map(AlimentoMapper::toDTO)
                .toList();
    }


}
