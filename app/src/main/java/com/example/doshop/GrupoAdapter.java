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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.doshop.domain.Grupo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoHolder> {
    private Context context;
    private List<Grupo> mDataset;
    public static final String _ABMC_GRUPO_MODO_KEY = "_ABMC_GRUPO_MODO_KEY";
    public static final int _KEY_CREAR_GRUPO = 1;
    public static final int _KEY_BORRAR_GRUPO = 2;
    public static final int _KEY_EDITAR_GRUPO = 3;
    public static final int _KEY_INVITAR_USUARIO = 4;
    public static final String _GRUPO_KEY = "_GRUPO_KEY";

    public static final String _ABMC_EVENTO_MODO_KEY = "_ABMC_EVENTO_MODO_KEY";
    public static final int _KEY_CREAR_EVENTO = 10;
    public static final int _KEY_BORRAR_EVENTO = 20;
    public static final int _KEY_EDITAR_EVENTO = 30;

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
        holder.tvGrupoAdmin.setText("Admin: " + grupo.getGrupoAdmin());

        // Corroborar si se es admin de un grupo para agregar
        // la gestión de grupo

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(grupo.getGrupoAdmin())){
            holder.loGrupoAdmin.setVisibility(View.VISIBLE);
        }
        else {
            holder.loGrupoAdmin.setVisibility(View.GONE);
        }


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
                Intent intent = new Intent(context, AbmcGrupo.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                intent.putExtra(_ABMC_GRUPO_MODO_KEY, _KEY_INVITAR_USUARIO );
                //SE EDITA EL GRUPO
                intent.putExtra(_GRUPO_KEY, grupo );
                ((Activity) context).startActivity(intent);

            }
        });
        ///////////////////////////////////////
        //CLICK EN BOTON AGREGAR EVENTO  //////
        ///////////////////////////////////////
        holder.bAgregarEvento.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AbmcEvento.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                intent.putExtra(_ABMC_EVENTO_MODO_KEY, _KEY_CREAR_EVENTO );
                intent.putExtra(_GRUPO_KEY, grupo);
                ((Activity) context).startActivity(intent);
            }
        });

    }
    public class GrupoHolder extends RecyclerView.ViewHolder {

        TextView tvGrupoNombre;
        TextView tvGrupoAdmin;
        Button bEditarGrupo;
        Button bEliminarGrupo;
        Button bVerLista;
        Button bInvitarUsuario;
        Button bAgregarEvento;
        LinearLayout loGrupoAdmin;

        public GrupoHolder(View base) {
            super(base);
            this.tvGrupoNombre = (TextView) base.findViewById(R.id.tvGrupoNombre);
            this.tvGrupoAdmin = (TextView) base.findViewById(R.id.tvGrupoAdmin);
            this.bEditarGrupo = (Button) base.findViewById(R.id.buttonEditarGrupo);
            this.bEliminarGrupo = (Button) base.findViewById(R.id.buttonEliminarGrupo);
            this.bVerLista = (Button) base.findViewById(R.id.buttonVerLista);
            this.bInvitarUsuario = (Button) base.findViewById(R.id.buttonInvitarUsuario);
            this.bAgregarEvento = (Button) base.findViewById(R.id.bAgregarEvento);
            this.loGrupoAdmin = (LinearLayout) base.findViewById(R.id.loGrupoAdmin);

        }
    }
}
