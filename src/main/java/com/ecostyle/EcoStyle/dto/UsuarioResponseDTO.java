package com.ecostyle.EcoStyle.dto;

import com.ecostyle.EcoStyle.model.TipoUsuario;

public class UsuarioResponseDTO {
    private String id;
    private String email;
    private String nombre;
    private String rut;
    private TipoUsuario tipo;
    private String password;
    private String token;

    public UsuarioResponseDTO(String id, String email, String nombre, String rut, TipoUsuario tipo) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rut = rut;
        this.tipo = tipo;
    }

    public UsuarioResponseDTO(String id, String email, String nombre, String rut, TipoUsuario tipo, String token) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rut = rut;
        this.tipo = tipo;
        this.token = token;
    }

    public UsuarioResponseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

