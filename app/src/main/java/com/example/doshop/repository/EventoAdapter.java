package com.example.doshop.repository;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doshop.GrupoAdapter;
import com.example.doshop.R;
import com.example.doshop.domain.Evento;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoHolder>{


    public EventoAdapter(List<Evento> listaDataSet) {
    }

    @NonNull
    @Override
    public EventoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class EventoHolder extends RecyclerView.ViewHolder {

        TextView tvNombreEvento;
        TextView tvPrioridad;
        Button bLista;
        Button bDetalles;

        public EventoHolder(View base) {
            super(base);
            this.tvNombreEvento = (TextView) base.findViewById(R.id.textViewNombreEvento);
            this.tvPrioridad = (TextView) base.findViewById(R.id.textViewPrioridad);
            this.bLista = (Button) base.findViewById(R.id.buttonLista);
            this.bDetalles = (Button) base.findViewById(R.id.buttonDetallesEvento);

        }
    }
}
