package com.pravi.backend.praviproject.DTO;

        public record DashboardResumoDTO(
                String nomeUsuario,
                String email,
                Long idFamilia,
                String nomeFamilia,
                int qtdMembros,
                int qtdAlimentosUsuario,
                int qtdAlimentosFamilia,
                String criadaEm,
                boolean temAlimentosVencendo,
                int qtdVencidosUsuario,     // NOVO
                int qtdVencidosFamilia      // NOVO
        ) {}

