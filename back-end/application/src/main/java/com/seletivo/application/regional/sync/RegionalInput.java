package com.seletivo.application.regional.sync;

public record RegionalInput(
        Long id,
        String nome
) {
    public static RegionalInput with(final Long id, final String nome) {
        return new RegionalInput(
                id,
                normalizeNome(nome)
        );
    }

    private static String normalizeNome(final String nome) {
        return nome == null ? null : nome.trim();
    }
}