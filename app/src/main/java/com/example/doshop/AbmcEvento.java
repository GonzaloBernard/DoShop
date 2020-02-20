package com.example.doshop;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doshop.domain.Evento;
import com.example.doshop.domain.Grupo;
import com.example.doshop.domain.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AbmcEvento extends AppCompatActivity {

    DatabaseReference databaseGrupos;
    private FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abmc_evento, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
        setContentView(R.layout.activity_abmc_evento);

        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.tbAbmcEvento);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "GESTIONAR EVENTO" );
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        String user = mAuth.getCurrentUser().getUid();

        // Referencia a la tabla grupos
        databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(user);
        final Evento evento;

        Bundle extras = getIntent().getExtras();
        Grupo grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);

        switch (extras.getInt(GrupoAdapter._ABMC_GRUPO_MODO_KEY)) {
            case GrupoAdapter._KEY_CREAR_EVENTO:
/*
                // EVENTO HARDCODEADO //
                evento = new Evento();
                evento.setEventoId("1");
                evento.setEventoNombre("Nombre evento HC");
                evento.setEventoDescripcion("Descripcion evento HC");

                databaseGrupos.child(id).setValue(grupo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("ERROR :::","onCompleteListener task Successful ");
                            Toast.makeText(AbmcGrupo.this, "Grupo creado ",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e("ERROR :::","onCompleteListener task unsuccessful ");
                            Toast.makeText(AbmcGrupo.this, "Error al crear grupo ",Toast.LENGTH_SHORT).show();
                        }

                    }
                });*/
                /*
                try {

                    // Insertar grupo en Firebase database
                    createGrupoFirebase(etNombreGrupo.getText().toString());
                    // Retornar a MisGrupos
                    finish();

                }
                catch (Exception e){
                    Toast.makeText(AbmcEvento.this, "Error !!!",Toast.LENGTH_SHORT).show();
                    Log.e("Error ::::: ",e.getMessage());
                }
                */
                break;
            case GrupoAdapter._KEY_BORRAR_EVENTO:
                /*
                String grupoIdBorrar = extras.getString(GrupoAdapter._GRUPO_KEY);
                //ELIMINAR EL GRUPO DE FIREBASE DATABASE
                databaseGrupos.child(grupoIdBorrar).removeValue();
                finish();
                */
                break;
            case GrupoAdapter._KEY_EDITAR_EVENTO:
                /*
                grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);
                // findViews
                buttonAltaGrupo = (Button) findViewById(R.id.bAltaGrupo);
                buttonAltaGrupo.setText("Editar grupo");
                etNombreGrupo = (EditText) findViewById(R.id.etNombreGrupo);
                etNombreGrupo.setText(grupo.getGrupoNombre());
                buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grupo.setGrupoNombre(etNombreGrupo.getText().toString());
                        databaseGrupos.child(grupo.getGrupoId()).setValue(grupo);
                        finish();
                    }
                });
                */
                break;
        }

    }


/*
    // Insertar usuario en al base de datos Firebase
    private void createGrupoFirebase(String nombreGrupo) {
        String id = databaseGrupos.push().getKey();
        Grupo grupo = new Grupo();
        grupo.setGrupoId(id);
        grupo.setGrupoNombre(nombreGrupo);
        grupo.setGrupoAdmin(mAuth.getCurrentUser().getEmail());
        grupo.addUsuarioInvitado(mAuth.getCurrentUser().getEmail());

        // Producto Hardcodeado //
        Producto productoHC = new Producto();
        productoHC.setProductoId("1");
        productoHC.setProductoNombre("Producto Hardcodeado");
        productoHC.setProductoDescripcion("Descripcion HardCodeada");
        // Producto Hardcodeado //

        // Evento Hardcodeado //
        Evento eventoHC = new Evento();
        eventoHC.addElemento(productoHC);
        eventoHC.setEventoId("1");
        eventoHC.setEventoNombre("Evento asdasd");

        grupo.addEvento(eventoHC);

        databaseGrupos.child(id).setValue(grupo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("ERROR :::","onCompleteListener task Successful ");
                    Toast.makeText(AbmcGrupo.this, "Grupo creado ",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("ERROR :::","onCompleteListener task unsuccessful ");
                    Toast.makeText(AbmcGrupo.this, "Error al crear grupo ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }*/
}