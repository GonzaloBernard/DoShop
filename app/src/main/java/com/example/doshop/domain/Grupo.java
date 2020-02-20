package com.example.doshop.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Grupo implements Parcelable {
    private String grupoId;
    private String grupoNombre;
    private String grupoAdmin;
    private List<Evento> listaEventos = new ArrayList<>();
    private List<String> usuariosInvitados = new ArrayList<>();

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

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public String getGrupoAdmin() {
        return grupoAdmin;
    }

    public void setGrupoAdmin(String grupoAdmin) {
        this.grupoAdmin = grupoAdmin;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public void addEvento(Evento evento){
        this.listaEventos.add(evento);
    }

    public List<String> getUsuariosInvitados() {
        return usuariosInvitados;
    }

    public void setUsuariosInvitados(List<String> usuariosInvitados) {
        this.usuariosInvitados = usuariosInvitados;
    }

    public void addUsuarioInvitado(String usuarioInvitadoId){
        this.usuariosInvitados.add(usuarioInvitadoId);
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
        dest.writeList(listaEventos);
        dest.writeList(usuariosInvitados);
    }

    private void readFromParcel(Parcel in) {
        this.grupoId = in.readString();
        this.grupoNombre = in.readString();
        this.grupoAdmin = in.readString();
        in.readList(this.listaEventos, this.getClass().getClassLoader());
        in.readList(this.usuariosInvitados, this.getClass().getClassLoader());
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
