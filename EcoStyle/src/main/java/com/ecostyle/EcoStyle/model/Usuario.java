package com.ecostyle.EcoStyle.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    private String id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    @OneToOne
    @JoinColumn(name = "persona_rut", referencedColumnName = "rut", foreignKey = @ForeignKey(name = "fk_usuario_persona"))
    public Persona persona;

    public Usuario() {
        this.id = "";
        this.email = "";
        this.password = "";
        this.tipo = TipoUsuario.USUARIO;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
