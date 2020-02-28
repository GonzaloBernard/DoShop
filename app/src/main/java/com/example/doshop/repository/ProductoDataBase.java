package com.example.doshop.repository;

import com.example.doshop.domain.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductoDataBase {
    private static ProductoDataBase _PRODUCTO_DATABASE = null;

    public static ProductoDataBase getInstance(){
        if(_PRODUCTO_DATABASE ==null) {
            _PRODUCTO_DATABASE = new ProductoDataBase();
        }
        return _PRODUCTO_DATABASE;
    }

    DatabaseReference databaseProductos = FirebaseDatabase.getInstance().getReference("productos");

    public void agregarProducto(Producto product){
        String eventoID = FirebaseAuth.getInstance().getUid();


    }




}
