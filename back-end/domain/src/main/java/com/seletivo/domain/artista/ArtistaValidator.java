package com.seletivo.domain.artista;

import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.ValidationHandler;
import com.seletivo.domain.validation.Validator;

public class ArtistaValidator extends Validator {
    private final Artista artista;

    public ArtistaValidator(final Artista artista, final ValidationHandler handler) {
        super(handler);
        this.artista = artista;
    }

    @Override
    public void validate() {
        if (this.artista.getNome() == null || this.artista.getNome().isBlank()) {
            this.validationHandler().append(new Error("'nome' should not be empty"));
        }
        if (this.artista.getTipo() == null || this.artista.getTipo().isBlank()) {
            this.validationHandler().append(new Error("'tipo' should not be empty"));
        }
    }
}