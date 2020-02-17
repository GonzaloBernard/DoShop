package com.example.doshop.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Grupo implements Parcelable {
    private String grupoId;
    private String grupoNombre;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(grupoId);
        dest.writeString(grupoNombre);
    }

    private void readFromParcel(Parcel in) {
        this.grupoId = in.readString();
        this.grupoNombre = in.readString();
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
