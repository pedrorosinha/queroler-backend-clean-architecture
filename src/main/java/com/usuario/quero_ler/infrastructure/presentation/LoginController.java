package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.usecases.user.LoginUseCase;
import com.usuario.quero_ler.infrastructure.dto.login.LoginRequestDto;
import com.usuario.quero_ler.infrastructure.dto.login.LoginResponseDto;
import com.usuario.quero_ler.infrastructure.security.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/logins")
public class LoginController {

    private final LoginUseCase loginUseCase;
    private final TokenService tokenService;

    @Value("${api.security.token.expiration-minutes:120}")
    private long tokenExpirationMinutes;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto dto,
            HttpServletResponse response) {

        var loginResult = loginUseCase.execute(dto.user(), dto.senha());

        String token = tokenService.generateToken(loginResult.user());

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(tokenExpirationMinutes))
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new LoginResponseDto(loginResult.primeiroLogin()));
    }
}
