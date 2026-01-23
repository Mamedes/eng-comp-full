package com.seletivo.infra.api.controller.login.request;

public record LoginRequest(String username, String password) {
    @Override
    public String toString() {
        return "LoginRequest[username=" + username + ", password=***]";
    }
}