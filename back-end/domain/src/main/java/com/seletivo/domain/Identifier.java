package com.seletivo.domain;

public abstract class Identifier extends ValueObject {

    public abstract Long getValue();

    public abstract String getSecureId();
}
