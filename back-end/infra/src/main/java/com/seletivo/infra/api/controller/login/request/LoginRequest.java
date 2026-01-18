package com.seletivo.infra.api.controller.login.request;

public record LoginRequest(String login, String password) {
    @Override
    public String toString() {
        return "LoginRequest[login=" + login + ", password=***]";
    }
}