package com.seletivo.application.artista.fetch.list.view;

import java.util.UUID;

public class ArtistaListView {
    private final UUID secureId;
    private final String nome;
    private final String tipo;
    private final Integer quantidadeAlbuns;

    public ArtistaListView(UUID secureId, String nome, String tipo, Integer quantidadeAlbuns) {
        this.secureId = secureId;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidadeAlbuns = quantidadeAlbuns;
    }

    public UUID getSecureId() { return secureId; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public Integer getQuantidadeAlbuns() { return quantidadeAlbuns; }
}