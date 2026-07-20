package com.usuario.quero_ler.infrastructure.security;

import com.usuario.quero_ler.core.gateway.UserGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  private final UserGateway userGateway;

  SecurityFilter(TokenService tokenService, UserGateway userGateway) {
    this.tokenService = tokenService;
    this.userGateway = userGateway;
  }

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var token = this.recoverToken(request);
    if (token != null) {
      var userName = tokenService.validateToken(token);
      var user = userGateway.findByUserIgnoreCase(userName).orElse(null);
      if (user != null) {
        var adapter = new UserDetailsAdapter(user);
        var authentication = new UsernamePasswordAuthenticationToken(adapter, null, adapter.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {

    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.replace("Bearer ", "");
    }

    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("jwt")) {
          return cookie.getValue();
        }
      }
    }

    return null;
  }
}
