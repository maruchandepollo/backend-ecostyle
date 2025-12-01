package com.ecostyle.EcoStyle.service;

import com.ecostyle.EcoStyle.dto.UsuarioDTO;
import com.ecostyle.EcoStyle.dto.UsuarioResponseDTO;
import com.ecostyle.EcoStyle.model.Persona;
import com.ecostyle.EcoStyle.model.TipoUsuario;
import com.ecostyle.EcoStyle.model.Usuario;
import com.ecostyle.EcoStyle.repository.PersonaRepository;
import com.ecostyle.EcoStyle.repository.UsuarioRepository;
import com.ecostyle.EcoStyle.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PersonaRepository personaRepository;
    private Util util = new Util();

    /**
     * Registrar un nuevo usuario
     */
    public UsuarioResponseDTO register(UsuarioDTO dto) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(dto.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        Persona persona = new Persona();
        persona.setRut(dto.getRut());
        persona.setName(dto.getNombre());
        personaRepository.save(persona);

        Usuario usuario = new Usuario();
        usuario.setId(util.generarID());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setTipo(dto.getTipo() != null ? dto.getTipo() : TipoUsuario.USUARIO);
        usuario.setPersona(persona);

        usuarioRepository.save(usuario);

        return convertToResponseDTO(usuario);
    }

    /**
     * Login de usuario
     */
    public UsuarioResponseDTO login(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return convertToResponseDTO(usuario);
    }

    /**
     * Listar todos los usuarios (sin contraseña)
     */
    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener usuario por ID
     */
    public UsuarioResponseDTO obtenerPorId(String id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return convertToResponseDTO(usuarioOpt.get());
    }

    /**
     * Actualizar usuario (solo admin puede actualizar otros usuarios)
     */
    public UsuarioResponseDTO actualizar(String id, UsuarioDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        if (dto.getTipo() != null) {
            usuario.setTipo(dto.getTipo());
        }

        Persona persona = usuario.getPersona();
        if (persona != null) {
            persona.setName(dto.getNombre());
            personaRepository.save(persona);
        }

        usuarioRepository.save(usuario);
        return convertToResponseDTO(usuario);
    }


    public void eliminar(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Desasociar la relación con Persona antes de eliminar
        if (usuario.getPersona() != null) {
            usuario.getPersona().setUsuario(null);
            personaRepository.save(usuario.getPersona());
        }

        usuarioRepository.delete(usuario);
    }

    /**
     * Buscar usuario por email
     */
    public UsuarioResponseDTO obtenerPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return convertToResponseDTO(usuarioOpt.get());
    }

    /**
     * Actualizar usuario por email
     */
    public UsuarioResponseDTO actualizarPorEmail(String email, UsuarioDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        if (dto.getTipo() != null) {
            usuario.setTipo(dto.getTipo());
        }

        Persona persona = usuario.getPersona();
        if (persona != null) {
            persona.setName(dto.getNombre());
            personaRepository.save(persona);
        }

        usuarioRepository.save(usuario);
        return convertToResponseDTO(usuario);
    }


    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setRut(usuario.getPersona() != null ? usuario.getPersona().getRut() : "");
        dto.setNombre(usuario.getPersona() != null ? usuario.getPersona().getName() : "");
        dto.setTipo(usuario.getTipo());
        dto.setPassword("*********");
        return dto;
    }
}
