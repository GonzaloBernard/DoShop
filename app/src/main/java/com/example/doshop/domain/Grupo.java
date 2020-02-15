package com.example.doshop.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Grupo {
    private Integer id;
    private String name;
    private ArrayList<Usuario> usuarios;

    public Grupo(){}

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){return id; }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){return name; }
}
