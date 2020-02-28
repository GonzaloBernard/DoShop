package com.example.doshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doshop.domain.Evento;
import com.example.doshop.domain.Grupo;
import com.example.doshop.repository.EventoAdapter;
import com.example.doshop.repository.GruposDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MisListas extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    DatabaseReference databaseEventos;
    DatabaseReference databaseGrupos;
    private List<Evento> listaDataSet;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGruposAgregarProducto:
                Intent i1 = new Intent(MisListas.this, AbmcProduct.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                i1.putExtra(ProductoAdapter._ABMC_PRODUCTO_MODO_KEY, ProductoAdapter._KEY_CREAR_PRODUCTO);
                startActivity(i1);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                Toast.makeText(this, ". . . . ", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);
/////////////////////////////////////////////////////////////////////
        /////////////NO SE COMO FUNCIONA LO QUE IRIA ACA ABAJO/////////////////////
/////////////////////////////////////////////////////////////////////
        /*listaDataSet = new ArrayList<>();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Usuario conectado
        String user = currentUser.getUid();
        //databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos");
        */
        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMisListas);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "EVENTOS" );
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.ListasRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MisListas.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    //@Override
    /*protected void onStart() {
        super.onStart();

        databaseEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDataSet.clear();
                String usuario = mAuth.getCurrentUser().getEmail();
                for(DataSnapshot grupoSnapshot: dataSnapshot.getChildren()){
                    for (DataSnapshot data: grupoSnapshot.getChildren()){
                        Evento evento = data.getValue(Evento.class);

                        // CORROBORAR QUE EL USUARIO ES MIEMBRO DEL GRUPO
                        if(evento.getGrupoPerteneciente().getUsuariosInvitados().contains(usuario)){
                            listaDataSet.add(evento);
                        }
                        // MUESTRA TODOS LOS GRUPOS
                        //listaDataSet.add(grupo);
                    }
                }
                mAdapter = new EventoAdapter(listaDataSet);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/



}
