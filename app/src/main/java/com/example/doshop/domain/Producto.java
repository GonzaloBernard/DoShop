package com.example.doshop.domain;

import java.util.Objects;

public class Producto {
    private Integer id;
    private String name;
    private String brand;
    private String description;

    public Producto(){}

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){return id; }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){return name; }

    public void setBrand(String brand){
        this.brand = brand;
    }
    public String getBrand(){return brand; }

    public void setDescription(String description){
        this.description =description;
    }
    public String getDescription(){return description; }
}
