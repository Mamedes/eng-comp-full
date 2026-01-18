package com.seletivo.application.artista.create;

public record CreateArtistaCommand(
        String nome,
        String tipo
) {
    public static CreateArtistaCommand with(final String nome, final String tipo) {
        return new CreateArtistaCommand(nome, tipo);
    }
}