package com.ecostyle.EcoStyle.controller;

import com.ecostyle.EcoStyle.dto.UsuarioDTO;
import com.ecostyle.EcoStyle.dto.UsuarioResponseDTO;
import com.ecostyle.EcoStyle.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO dto) {
        try {
            UsuarioResponseDTO usuario = usuarioService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            UsuarioResponseDTO usuario = usuarioService.login(email, password);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        try {
            List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al listar usuarios"));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> obtenerPorEmail(@PathVariable String email) {
        try {
            UsuarioResponseDTO usuario = usuarioService.obtenerPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/editar/{email}")
    public ResponseEntity<?> actualizar(@PathVariable String email, @RequestBody UsuarioDTO dto) {
        try {
            UsuarioResponseDTO usuario = usuarioService.actualizarPorEmail(email, dto);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{email}")
    public ResponseEntity<?> eliminar(@PathVariable String email) {
        try {
            usuarioService.eliminar(email);
            return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}

