package com.example.restsimple.application.port.in;

public interface LoginUseCase {
    LoginResponse login(LoginCommand command);
}