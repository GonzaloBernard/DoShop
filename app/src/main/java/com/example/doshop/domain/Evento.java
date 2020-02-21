package com.example.doshop.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Evento implements Parcelable{

    public Evento() {
    }

    private String eventoId;
    private String eventoNombre;
    private String eventoDescripcion;
    // Se debería cambiar de <Producto> a <Elemento> cuando se agregue la clase padre Elemento al modelo
    private List<Producto> listaElementos = new ArrayList<>();
    private EventoTipo eventoTipo;
    private EventoPrioridad eventoPrioridad;
    private String usuarioResponsableId;

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public List<?> getListaElementos() {
        return listaElementos;
    }

    public void setListaElementos(List<Producto> listaElementos) {
        this.listaElementos = listaElementos;
    }

    public void addElemento(Producto elemento){
        this.listaElementos.add(elemento);
    }

    public String getEventoNombre() {
        return eventoNombre;
    }

    public void setEventoNombre(String eventoNombre) {
        this.eventoNombre = eventoNombre;
    }

    public String getEventoDescripcion() {
        return eventoDescripcion;
    }

    public void setEventoDescripcion(String eventoDescripcion) {
        this.eventoDescripcion = eventoDescripcion;
    }

    public EventoTipo getEventoTipo() {
        return eventoTipo;
    }

    public void setEventoTipo(EventoTipo eventoTipo) {
        this.eventoTipo = eventoTipo;
    }

    public EventoPrioridad getEventoPrioridad() {
        return eventoPrioridad;
    }

    public void setEventoPrioridad(EventoPrioridad eventoPrioridad) {
        this.eventoPrioridad = eventoPrioridad;
    }

    public String getUsuarioResponsableId() {
        return usuarioResponsableId;
    }

    public void setUsuarioResponsableId(String usuarioResponsableId) {
        this.usuarioResponsableId = usuarioResponsableId;
    }

    public Evento(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventoId);
        dest.writeString(eventoNombre);
        dest.writeString(eventoDescripcion);
        dest.writeList(listaElementos);
        dest.writeParcelable(eventoTipo,0);
        dest.writeParcelable(eventoPrioridad,0);
        dest.writeString(usuarioResponsableId);

    }

    private void readFromParcel(Parcel in) {
        this.eventoId = in.readString();
        this.eventoNombre = in.readString();
        this.eventoDescripcion = in.readString();
        in.readList(this.listaElementos, this.getClass().getClassLoader());
        this.eventoTipo = in.readParcelable(this.getClass().getClassLoader());
        this.eventoPrioridad = in.readParcelable(this.getClass().getClassLoader());
        this.usuarioResponsableId = in.readString();

    }

    public static final Parcelable.Creator<Evento> CREATOR = new Parcelable.Creator<Evento>() {
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };
}
