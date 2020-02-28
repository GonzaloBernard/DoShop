package com.example.doshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
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
            holder.bFilaGrupoMenu.setVisibility(View.VISIBLE);
        }
        else {
            holder.bFilaGrupoMenu.setVisibility(View.GONE);
        }

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


        ////////////////////////////////////////
        // MENU DE OPCIONES ////////////////////
        // GESTION DE GRUPOS ///////////////////
        ////////////////////////////////////////
        holder.bFilaGrupoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.bFilaGrupoMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_gestion_grupos);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuGestionGruposAgregarEvento:
                                Intent i1 = new Intent(context, AbmcEvento.class);
                                //EL MODO DETERMINA LA ACCION A REALIZAR
                                i1.putExtra(_ABMC_EVENTO_MODO_KEY, _KEY_CREAR_EVENTO );
                                i1.putExtra(_GRUPO_KEY, grupo);
                                ((Activity) context).startActivity(i1);
                                return true;
                            case R.id.menuGestionGruposEditarGrupos:
                                Intent intent = new Intent(context, AbmcGrupo.class);
                                //EL MODO DETERMINA LA ACCION A REALIZAR
                                intent.putExtra(_ABMC_GRUPO_MODO_KEY, _KEY_EDITAR_GRUPO );
                                //SE EDITA EL GRUPO
                                intent.putExtra(_GRUPO_KEY, grupo );
                                ((Activity) context).startActivity(intent);
                                return true;
                            case R.id.menuGestionGruposEliminarGrupo:
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

                                return true;
                            case R.id.menuGestionGruposInvitarUsuario:
                                Intent i4 = new Intent(context, AbmcGrupo.class);
                                //EL MODO DETERMINA LA ACCION A REALIZAR
                                i4.putExtra(_ABMC_GRUPO_MODO_KEY, _KEY_INVITAR_USUARIO );
                                //SE EDITA EL GRUPO
                                i4.putExtra(_GRUPO_KEY, grupo );
                                ((Activity) context).startActivity(i4);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();

            }
        });

    }


    public class GrupoHolder extends RecyclerView.ViewHolder {

        TextView tvGrupoNombre;
        TextView tvGrupoAdmin;
        Button bVerLista;
        Button bFilaGrupoMenu;

        public GrupoHolder(View base) {
            super(base);
            this.tvGrupoNombre = (TextView) base.findViewById(R.id.tvGrupoNombre);
            this.tvGrupoAdmin = (TextView) base.findViewById(R.id.tvGrupoAdmin);
            this.bVerLista = (Button) base.findViewById(R.id.buttonVerEventos);
            this.bFilaGrupoMenu = (Button) base.findViewById(R.id.bFilaGrupoMenu);

        }
    }
}
