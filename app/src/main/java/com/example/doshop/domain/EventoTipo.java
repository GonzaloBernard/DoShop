package com.example.doshop.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class EventoTipo implements Parcelable {
    String eventoTipoId;
    String eventoTipo;

    public EventoTipo() {}

    public EventoTipo(Parcel in){
        readFromParcel(in);
    }

    public String getEventoTipoId() {
        return eventoTipoId;
    }

    public void setEventoTipoId(String eventoTipoId) {
        this.eventoTipoId = eventoTipoId;
    }

    public String getEventoTipo() {
        return eventoTipo;
    }

    public void setEventoTipo(String eventoTipo) {
        this.eventoTipo = eventoTipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    private void readFromParcel(Parcel in) {
    }

    public static final Parcelable.Creator<EventoTipo> CREATOR = new Parcelable.Creator<EventoTipo>() {
        public EventoTipo createFromParcel(Parcel in) {
            return new EventoTipo(in);
        }

        public EventoTipo[] newArray(int size) {
            return new EventoTipo[size];
        }
    };
}
