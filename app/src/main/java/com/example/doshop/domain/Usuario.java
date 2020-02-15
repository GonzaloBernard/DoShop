package com.example.doshop.domain;

import java.util.Objects;

public class Usuario {
    private Integer id;
    private String name;

    public Usuario(){}

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){return id; }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){return name; }

}
