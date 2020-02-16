package com.example.doshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doshop.domain.Grupo;

import java.util.List;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoHolder> {
    private Context context;
    private List<Grupo> mDataset;

    public GrupoAdapter(List<Grupo> myDataset) {
        this.mDataset = myDataset;
    }
    @Override
    public GrupoAdapter.GrupoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_grupo, parent, false);
        GrupoHolder vh = new GrupoHolder(v);
        context=parent.getContext();
        return vh;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onBindViewHolder(final GrupoHolder holder, final int position) {
        final Grupo grupo = mDataset.get(position);
        holder.grupoNombre.setText(grupo.getGrupoNombre());

    }


    public class GrupoHolder extends RecyclerView.ViewHolder {

        TextView grupoNombre;

        public GrupoHolder(View base) {
            super(base);
            this.grupoNombre = (TextView) base.findViewById(R.id.tvGrupoNombre);

        }
    }
}
