package com.upc.molinachirinostp.security.controllers;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.security.dtos.AuthResponse;
import com.upc.molinachirinostp.security.dtos.LoginRequest;
import com.upc.molinachirinostp.security.dtos.RegisterRequest;
import com.upc.molinachirinostp.security.dtos.ResetPasswordRequest;
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

        // IMPORTANTE: Limpiar la contraseña antes de devolver al cliente
        // Nunca exponer contraseñas encriptadas en respuestas
        saved.setPassword(null);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Temporary password reset endpoint for testing. Requires resetSecret match with application property.
    // WARNING: This endpoint should be disabled in production or protected with proper email verification
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req, @org.springframework.beans.factory.annotation.Value("${app.resetSecret:dev-reset-secret}") String resetSecret) {
        // Validación mejorada y segura
        if (req.getSecret() == null || req.getSecret().trim().isEmpty()) {
            return ResponseEntity.status(403).body("Invalid reset secret");
        }

        // Usar equals de forma segura (ya protegido del null en el if anterior)
        if (!req.getSecret().equals(resetSecret)) {
            return ResponseEntity.status(403).body("Invalid reset secret");
        }

        // Validar que la nueva contraseña no esté vacía
        if (req.getNewPassword() == null || req.getNewPassword().trim().isEmpty()) {
            return ResponseEntity.status(400).body("Password cannot be empty");
        }

        boolean ok = authService.resetPassword(req.getEmail(), req.getNewPassword());
        if (ok) return ResponseEntity.ok("Password updated");
        return ResponseEntity.status(404).body("User not found");
    }
}
