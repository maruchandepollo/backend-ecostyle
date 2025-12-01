package com.ecostyle.EcoStyle.controller;

import com.ecostyle.EcoStyle.dto.ProductoDTO;
import com.ecostyle.EcoStyle.service.ProductoService;
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


    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ProductoDTO dto) {
        try {
            ProductoDTO productoCreado = productoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al crear producto"));
        }
    }


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


    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        try {
            ProductoDTO productoActualizado = productoService.actualizar(id, dto);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

