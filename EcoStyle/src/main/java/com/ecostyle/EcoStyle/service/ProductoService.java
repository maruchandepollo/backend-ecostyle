package com.ecostyle.EcoStyle.service;

import com.ecostyle.EcoStyle.dto.ProductoDTO;
import com.ecostyle.EcoStyle.model.Producto;
import com.ecostyle.EcoStyle.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Crear nuevo producto
     */
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setName(dto.getName());
        producto.setPrice(dto.getPrice());
        producto.setDescription(dto.getDescription());

        Producto productoGuardado = productoRepository.save(producto);
        return convertToDTO(productoGuardado);
    }

    /**
     * Listar todos los productos
     */
    public List<ProductoDTO> listarTodos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener producto por ID
     */
    public ProductoDTO obtenerPorId(Long id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (!productoOpt.isPresent()) {
            throw new RuntimeException("Producto no encontrado");
        }
        return convertToDTO(productoOpt.get());
    }

    /**
     * Actualizar producto (solo admin)
     */
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (!productoOpt.isPresent()) {
            throw new RuntimeException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        producto.setName(dto.getName());
        producto.setPrice(dto.getPrice());
        producto.setDescription(dto.getDescription());

        Producto productoActualizado = productoRepository.save(producto);
        return convertToDTO(productoActualizado);
    }

    /**
     * Eliminar producto (solo admin)
     */
    public void eliminar(Long id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (!productoOpt.isPresent()) {
            throw new RuntimeException("Producto no encontrado");
        }

        productoRepository.deleteById(id);
    }

    /**
     * Convertir Producto a ProductoDTO
     */
    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setName(producto.getName());
        dto.setPrice(producto.getPrice());
        dto.setDescription(producto.getDescription());
        return dto;
    }
}
