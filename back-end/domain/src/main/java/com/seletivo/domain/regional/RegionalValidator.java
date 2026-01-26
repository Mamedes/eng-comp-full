package com.seletivo.domain.regional;


import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.ValidationHandler;
import com.seletivo.domain.validation.Validator;

public class RegionalValidator extends Validator {
    private final Regional regional;

    public RegionalValidator(final Regional regional, final ValidationHandler handler) {
        super(handler);
        this.regional = regional;
    }

    @Override
    public void validate() {
        if (this.regional.getExternalId() == null) {
            this.validationHandler().append(new Error("'externalId' should not be null"));
        }
        if (this.regional.getNome() == null || this.regional.getNome().isBlank()) {
            this.validationHandler().append(new Error("'nome' should not be empty"));
        }
        if (this.regional.getNome() != null && this.regional.getNome().length() > 200) {
            this.validationHandler().append(new Error("'nome' must be between 1 and 200 characters"));
        }
    }
}