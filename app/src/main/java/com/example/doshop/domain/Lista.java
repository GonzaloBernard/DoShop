package com.example.doshop.domain;

import java.util.Objects;

public class Lista {
    private Integer id;
    private String name;
    private String description;

    public Lista(){}

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){return id; }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){return name; }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){return description; }


}
