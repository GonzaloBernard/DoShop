package com.example.doshop.domain;

import java.util.ArrayList;

public class Grupo {
    private Integer grupoId;
    private String grupoNombre;
    private ArrayList<Usuario> usuarios;

    public Grupo(){}

    public void setGrupoId(Integer grupoId){
        this.grupoId = grupoId;
    }
    public Integer getGrupoId(){return grupoId; }

    public void setGrupoNombre(String grupoNombre){
        this.grupoNombre = grupoNombre;
    }
    public String getGrupoNombre(){return grupoNombre; }
}
