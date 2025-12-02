package com.ecostyle.EcoStyle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Persona {
    @Id
    private String rut;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToOne(mappedBy = "persona")
    public Usuario usuario;

    public Persona() {
        this.rut = "";
        this.nombre = "";
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
