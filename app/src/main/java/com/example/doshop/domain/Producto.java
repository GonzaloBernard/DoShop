package com.example.doshop.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import java.util.Objects;

public class Producto implements Parcelable {
    private String productoId;
    private String productoNombre;
    private String productoDescripcion;
    private Float productoPrecio;
    private String imagenProducto;



    public Producto(){}

    public Producto(Parcel in){
        readFromParcel(in);
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getProductoDescripcion() {
        return productoDescripcion;
    }

    public void setProductoDescripcion(String productoDescripcion) {
        this.productoDescripcion = productoDescripcion;
    }

    public Float getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(Float productoPrecio) {
        this.productoPrecio = Float.valueOf(String.valueOf(productoPrecio));
    }

    public String getImagenProducto(){return imagenProducto;}

    public void setImagenProducto(String imgProduct){
        this.imagenProducto = imgProduct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productoId);
        dest.writeString(productoNombre);
        dest.writeString(productoDescripcion);
        //dest.writeFloat(productoPrecio);
        dest.writeString(imagenProducto);
    }

    private void readFromParcel(Parcel in) {
        this.productoId = in.readString();
        this.productoNombre = in.readString();
        this.productoDescripcion = in.readString();
        //this.productoPrecio = in.readFloat();
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
