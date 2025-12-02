package com.ecostyle.EcoStyle.controller;

import com.ecostyle.EcoStyle.dto.ProductoDTO;
import com.ecostyle.EcoStyle.service.ProductoService;
import com.ecostyle.EcoStyle.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ SOLO ADMIN
    @PostMapping("/crear")
    public ResponseEntity<?> crear(
            @RequestBody ProductoDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (!validarAdmin(authHeader)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Solo administradores pueden crear productos"));
            }

            ProductoDTO productoCreado = productoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al crear producto"));
        }
    }

    // ✅ PÚBLICO
    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        try {
            List<ProductoDTO> productos = productoService.listarTodos();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al listar productos"));
        }
    }

    // ✅ PÚBLICO
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ProductoDTO producto = productoService.obtenerPorId(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ SOLO ADMIN
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody ProductoDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (!validarAdmin(authHeader)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Solo administradores pueden editar productos"));
            }

            ProductoDTO productoActualizado = productoService.actualizar(id, dto);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ SOLO ADMIN
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (!validarAdmin(authHeader)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Solo administradores pueden eliminar productos"));
            }

            productoService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado exitosamente"));
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
