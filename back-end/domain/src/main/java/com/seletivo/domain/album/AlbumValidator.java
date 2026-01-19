package com.seletivo.domain.album;

import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.ValidationHandler;
import com.seletivo.domain.validation.Validator;

public class AlbumValidator extends Validator {

    private final Album album;


    public AlbumValidator(final Album album, final ValidationHandler handler) {
        super(handler);
        this.album = album;
    }

    @Override
    public void validate() {
        if (this.album.getTitulo() == null || this.album.getTitulo().isBlank()) {
            this.validationHandler().append(new Error("'nome' should not be empty"));
        }

    }
}