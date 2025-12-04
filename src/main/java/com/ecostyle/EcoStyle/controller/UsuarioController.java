package com.ecostyle.EcoStyle.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecostyle.EcoStyle.dto.UsuarioDTO;
import com.ecostyle.EcoStyle.dto.UsuarioResponseDTO;
import com.ecostyle.EcoStyle.service.UsuarioService;
import com.ecostyle.EcoStyle.util.JwtUtil;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    // PÚBLICO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO dto) {
        try {
            UsuarioResponseDTO usuario = usuarioService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // PÚBLICO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            UsuarioResponseDTO usuario = usuarioService.login(email, password);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // SOLO ADMIN
    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (!validarAdmin(authHeader)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Solo administradores pueden listar usuarios"));
            }

            List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al listar usuarios"));
        }
    }

    // PÚBLICO
    @GetMapping("/{email}")
    public ResponseEntity<?> obtenerPorEmail(@PathVariable String email) {
        try {
            UsuarioResponseDTO usuario = usuarioService.obtenerPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // SOLO ADMIN
    @PutMapping("/editar/{email}")
    public ResponseEntity<?> actualizar(
            @PathVariable String email,
            @RequestBody UsuarioDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (!validarAdmin(authHeader)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Solo administradores pueden editar usuarios"));
            }

            UsuarioResponseDTO usuario = usuarioService.actualizarPorEmail(email, dto);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // SOLO ADMIN
    @DeleteMapping("/eliminar/{email}")
    public ResponseEntity<?> eliminar(
            @PathVariable String email,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (!validarAdmin(authHeader)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Solo administradores pueden eliminar usuarios"));
            }

            usuarioService.eliminar(email);
            return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Método helper para validar admin
    private boolean validarAdmin(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        return jwtUtil.isAdmin(token);
    }
}
