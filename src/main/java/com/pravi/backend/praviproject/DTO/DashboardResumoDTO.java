package com.pravi.backend.praviproject.DTO;

public record DashboardResumoDTO(
        String nomeUsuario,
        String email,
        Long idFamilia,
        String nomeFamilia,
        int qtdMembros,
        int qtdAlimentosUsuario,   // card "Alimentos cadastrados" (usuário)
        int qtdAlimentosFamilia,   // card "Minha família" (família)
        String criadaEm
) {
}