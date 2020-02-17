package com.example.doshop;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doshop.domain.Grupo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MisGrupos extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Button buttonCrearGrupo;
    private List<Grupo> listaDataSet;
    private RecyclerView.Adapter mAdapter;
    DatabaseReference databaseGrupos;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);

        //Inicializo la lista de grupos
        listaDataSet = new ArrayList<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Usuario conectado
        String user = currentUser.getUid();

        databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(user);

        // findViews
        buttonCrearGrupo = (Button) findViewById(R.id.buttonCrearGrupo);

        // RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.GruposRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MisGrupos.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        buttonCrearGrupo.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisGrupos.this, AbmcGrupo.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                i.putExtra(GrupoAdapter._ABMC_GRUPO_MODO_KEY, GrupoAdapter._KEY_CREAR_GRUPO );
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseGrupos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDataSet.clear();
                for(DataSnapshot grupoSnapshot: dataSnapshot.getChildren()){
                    Grupo grupo = grupoSnapshot.getValue(Grupo.class);
                    listaDataSet.add(grupo);
                }
                mAdapter = new GrupoAdapter( listaDataSet );
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
