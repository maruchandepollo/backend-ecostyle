package com.ecostyle.EcoStyle.dto;

import com.ecostyle.EcoStyle.model.TipoUsuario;

public class UsuarioDTO {
    private String email;
    private String password;
    private String rut;
    private String nombre;
    private TipoUsuario tipo;

    public UsuarioDTO() {
        this.email = "";
        this.password = "";
        this.rut = "";
        this.nombre = "";
        this.tipo = TipoUsuario.USUARIO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}
