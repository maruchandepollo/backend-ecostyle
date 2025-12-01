package com.ecostyle.EcoStyle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Persona {
    @Id
    private String rut;
    private String name;

    @OneToOne(mappedBy = "persona")
    public Usuario usuario;

    public Persona() {
        this.rut = "";
        this.name = "";
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
