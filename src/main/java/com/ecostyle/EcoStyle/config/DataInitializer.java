package com.ecostyle.EcoStyle.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecostyle.EcoStyle.model.Persona;
import com.ecostyle.EcoStyle.model.TipoUsuario;
import com.ecostyle.EcoStyle.model.Usuario;
import com.ecostyle.EcoStyle.repository.PersonaRepository;
import com.ecostyle.EcoStyle.repository.UsuarioRepository;
import com.ecostyle.EcoStyle.util.Util;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PersonaRepository personaRepository;
    
    private final Util util = new Util();

    @Override
    public void run(String... args) throws Exception {
        // Crear admin por defecto
        crearAdminPorDefecto();
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
}
