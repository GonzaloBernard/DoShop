package com.example.doshop.domain;

import java.util.ArrayList;

public class Grupo {
    private String grupoId;
    private String grupoNombre;
    private ArrayList<Usuario> usuarios;

    public Grupo(){}

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }
    public String getGrupoId(){return grupoId; }

    public void setGrupoNombre(String grupoNombre){
        this.grupoNombre = grupoNombre;
    }
    public String getGrupoNombre(){return grupoNombre; }
}
