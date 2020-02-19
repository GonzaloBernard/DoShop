package com.example.doshop.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Grupo implements Parcelable {
    private String grupoId;
    private String grupoNombre;
    private String grupoAdmin;
    private List<Producto> listaProductos;
    private List<String> idUsuariosInvitados = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        Grupo grupo = (Grupo) o;
        if (this.getGrupoId() == grupo.getGrupoId() ) return true;
        else return false;
    }

    public Grupo(){}

    public Grupo(Parcel in){
        readFromParcel(in);
    }

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }
    public String getGrupoId(){return grupoId; }

    public void setGrupoNombre(String grupoNombre){
        this.grupoNombre = grupoNombre;
    }
    public String getGrupoNombre(){return grupoNombre; }

    public void setGrupoAdmin(String grupoAdmin) {
        this.grupoAdmin = grupoAdmin;
    }
    public String getGrupoAdmin() {
        return grupoAdmin;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }
    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    public void addListaProductos(Producto productoId){
        this.listaProductos.add(productoId);
    }

    public List<String> getidUsuariosInvitados() {
        return idUsuariosInvitados;
    }
    public void setidUsuariosInvitados(List<String> idUsuariosInvitados) {
        this.idUsuariosInvitados = idUsuariosInvitados;
    }
    public void addidUsuariosInvitados(String usuarioInvitadoId){
        this.idUsuariosInvitados.add(usuarioInvitadoId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(grupoId);
        dest.writeString(grupoNombre);
        dest.writeString(grupoAdmin);
        dest.writeList(listaProductos);
        dest.writeList(idUsuariosInvitados);
    }

    private void readFromParcel(Parcel in) {
        this.grupoId = in.readString();
        this.grupoNombre = in.readString();
        this.grupoAdmin = in.readString();
        in.readList(this.listaProductos, this.getClass().getClassLoader());
        in.readList(this.idUsuariosInvitados, this.getClass().getClassLoader());
    }

    public static final Parcelable.Creator<Grupo> CREATOR = new Parcelable.Creator<Grupo>() {
        public Grupo createFromParcel(Parcel in) {
            return new Grupo(in);
        }

        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };
}
