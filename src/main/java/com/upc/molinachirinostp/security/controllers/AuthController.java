package com.upc.molinachirinostp.security.controllers;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.security.dtos.AuthResponse;
import com.upc.molinachirinostp.security.dtos.LoginRequest;
import com.upc.molinachirinostp.security.dtos.RegisterRequest;
import com.upc.molinachirinostp.security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody RegisterRequest req) {
        Usuario u = new Usuario();
        u.setPrimerNombre(req.getPrimerNombre());
        u.setPrimerApellido(req.getPrimerApellido());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        Usuario saved = authService.register(u);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
