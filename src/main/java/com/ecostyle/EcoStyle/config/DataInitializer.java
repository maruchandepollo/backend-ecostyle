package com.ecostyle.EcoStyle.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecostyle.EcoStyle.model.Persona;
import com.ecostyle.EcoStyle.model.Producto;
import com.ecostyle.EcoStyle.model.TipoUsuario;
import com.ecostyle.EcoStyle.model.Usuario;
import com.ecostyle.EcoStyle.repository.PersonaRepository;
import com.ecostyle.EcoStyle.repository.ProductoRepository;
import com.ecostyle.EcoStyle.repository.UsuarioRepository;
import com.ecostyle.EcoStyle.util.Util;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    private final Util util = new Util();

    @Override
    public void run(String... args) throws Exception {
        // Crear admin por defecto
        crearAdminPorDefecto();
        // Cargar productos iniciales
        cargarProductosIniciales();
    }

    private void cargarProductosIniciales() {
        if (productoRepository.count() == 0) {
            // Crear productos
            crearProducto("Monstera Deliciosa", 24990L, 10L, "Planta tropical de hojas perforadas", "/assets/images/monstera2.jpg", 20L);
            crearProducto("Sansevieria Trifasciata", 15725L, 15L, "Lengua de tigre - Purificadora de aire", "/assets/images/sansevieria-trifasciata.jpg", 0L);
            crearProducto("Poto Dorado", 13590L, 5L, "Planta colgante de fácil cuidado", "/assets/images/Poto-dorado.jpg", 0L);
            crearProducto("Alocasia Polly", 32500L, 8L, "Planta ornamental de hojas grandes", "/assets/images/alocasia-polly.jpg", 30L);
            crearProducto("Schlumbergera Truncata", 21990L, 0L, "Una planta muy apreciada por su belleza", "/assets/images/schlumbergera-truncata.jpg", 0L);
            crearProducto("Ficus Lyrata", 28990L, 12L, "Árbol de interior elegante", "/assets/images/ficuslyrata.jpg", 0L);
            crearProducto("Areca Palm", 33990L, 0L, "Palmera purificadora que da un toque tropical al hogar", "/assets/images/arecapalm.JPG", 0L);
            crearProducto("Calathea Orbifolia", 27500L, 7L, "Planta de hojas anchas y patrón elegante, necesita humedad", "/assets/images/calathea-orbifolia.jpg", 0L);
            crearProducto("Helecho Boston", 16990L, 6L, "Planta clásica con hojas delicadas", "/assets/images/helecho-boston.jpg", 25L);
            crearProducto("Bambú de la Suerte", 12990L, 20L, "Planta de interior que atrae la buena suerte", "/assets/images/bambu.jpg", 0L);

            System.out.println("Productos iniciales cargados correctamente");
        }
    }
    
    private void crearAdminPorDefecto() {
        Optional<Usuario> adminExistente = usuarioRepository.findByEmail("admin@ecostyle.com");
        
        if (!adminExistente.isPresent()) {
            Persona personaAdmin = new Persona();
            personaAdmin.setRut("12345678-K");
            personaAdmin.setNombre("Administrador EcoStyle");
            personaRepository.save(personaAdmin);

            Usuario admin = new Usuario();
            admin.setId(util.generarID());
            admin.setEmail("admin@ecostyle.com");
            admin.setPassword("admin123");
            admin.setTipo(TipoUsuario.ADMIN);
            admin.setPersona(personaAdmin);
            
            usuarioRepository.save(admin);
            System.out.println("Admin creado: admin@ecostyle.com / admin123");
        }
    }



    private void crearProducto(String nombre, Long precio, Long stock, String descripcion, String img, Long descuento) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
        producto.setImg(img);
        producto.setDescuento(descuento);
        productoRepository.save(producto);
    }
}
