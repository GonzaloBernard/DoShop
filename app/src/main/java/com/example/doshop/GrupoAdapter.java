package com.example.doshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.doshop.domain.Grupo;
import com.example.doshop.domain.Usuario;

import java.util.List;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoHolder> {
    private Context context;
    private List<Grupo> mDataset;
    public static final String _ABMC_GRUPO_MODO_KEY = "_ABMC_GRUPO_MODO_KEY";
    public static final int _KEY_CREAR_GRUPO = 1;
    public static final int _KEY_BORRAR_GRUPO = 2;
    public static final int _KEY_EDITAR_GRUPO = 3;
    public static final String _GRUPO_KEY = "_GRUPO_KEY";

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
        holder.tvGrupoNombre.setText(grupo.getGrupoNombre());


        ///////////////////////////////
        //CLICK EN BOTON ELIMINAR//////
        ///////////////////////////////
        holder.bEliminarGrupo.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Quiere eliminar el grupo?")
                        .setTitle("ELIMINAR GRUPO")
                        .setPositiveButton("ELIMINAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        Intent intent = new Intent(context, AbmcGrupo.class);
                                        //EL MODO DETERMINA LA ACCION A REALIZAR
                                        intent.putExtra(_ABMC_GRUPO_MODO_KEY, _KEY_BORRAR_GRUPO );
                                        //SE ELIMINA EL GRUPO
                                        intent.putExtra(_GRUPO_KEY, grupo.getGrupoId());
                                        ((Activity) context).startActivity(intent);
                                    }
                                }).setNegativeButton("CONSERVAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlgInt, int i) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        ///////////////////////////////
        //CLICK EN BOTON EDITAR//////
        ///////////////////////////////
        holder.bEditarGrupo.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Quiere editar el grupo?")
                        .setTitle("EDITAR GRUPO")
                        .setPositiveButton("EDITAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        Intent intent = new Intent(context, AbmcGrupo.class);
                                        //EL MODO DETERMINA LA ACCION A REALIZAR
                                        intent.putExtra(_ABMC_GRUPO_MODO_KEY, _KEY_EDITAR_GRUPO );
                                        //SE EDITA EL GRUPO
                                        intent.putExtra(_GRUPO_KEY, grupo );
                                        ((Activity) context).startActivity(intent);
                                    }
                                }).setNegativeButton("CONSERVAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlgInt, int i) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        ///////////////////////////////
        //CLICK EN BOTON VER LISTA//////
        ///////////////////////////////
        holder.bVerLista.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MisListas.class);
                ((Activity) context).startActivity(intent);
            }
        });
        ///////////////////////////////////////
        //CLICK EN BOTON INVITAR USUARIO //////
        ///////////////////////////////////////
        holder.bInvitarUsuario.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // USUARIO INVITADO HARDCODEADO

                grupo.getidUsuariosInvitados().add("-M0GOUwvYfBSCsRm_OJF");
                grupo.getidUsuariosInvitados().add("-M0GOF6tMB93OxzOw85F");

                Intent intent = new Intent(context, AbmcGrupo.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                intent.putExtra(_ABMC_GRUPO_MODO_KEY, _KEY_EDITAR_GRUPO );
                //SE EDITA EL GRUPO
                intent.putExtra(_GRUPO_KEY, grupo );
                ((Activity) context).startActivity(intent);

            }
        });

    }
    public class GrupoHolder extends RecyclerView.ViewHolder {

        TextView tvGrupoNombre;
        Button bEditarGrupo;
        Button bEliminarGrupo;
        Button bVerLista;
        Button bInvitarUsuario;

        public GrupoHolder(View base) {
            super(base);
            this.tvGrupoNombre = (TextView) base.findViewById(R.id.tvGrupoNombre);
            this.bEditarGrupo = (Button) base.findViewById(R.id.bEditarGrupo);
            this.bEliminarGrupo = (Button) base.findViewById(R.id.bEliminarGrupo);
            this.bVerLista = (Button) base.findViewById(R.id.bVerLista);
            this.bInvitarUsuario = (Button) base.findViewById(R.id.bInvitarUsuario);

        }
    }
}
