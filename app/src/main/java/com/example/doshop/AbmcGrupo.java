package com.example.doshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doshop.domain.Grupo;
import com.example.doshop.domain.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AbmcGrupo extends AppCompatActivity {

    DatabaseReference databaseGrupos;
    private Button buttonAltaGrupo;
    private EditText etNombreGrupo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abmc_grupo);

        final Resources resources = getResources();
        Bundle extras = getIntent().getExtras();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Usuario conectado
        String user = currentUser.getUid();
        // Referencia a la tabla grupos
        databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(user);
        final Grupo grupo;
        switch (extras.getInt(GrupoAdapter._ABMC_GRUPO_MODO_KEY)) {
            case GrupoAdapter._KEY_CREAR_GRUPO:
                try {
                    // findViews
                    buttonAltaGrupo = (Button) findViewById(R.id.bAltaGrupo);
                    buttonAltaGrupo.setText("Crear grupo");
                    etNombreGrupo = (EditText) findViewById(R.id.etNombreGrupo);
                    buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Insertar grupo en Firebase database
                            String grupoId = createGrupoFirebase(etNombreGrupo.getText().toString());
                            // Retornar a MisGrupos
                            finish();
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(AbmcGrupo.this, "Error !!!",Toast.LENGTH_SHORT).show();
                    Log.e("Error ::::: ",e.getMessage());
                }
                break;
            case GrupoAdapter._KEY_BORRAR_GRUPO:
                String grupoIdBorrar = extras.getString(GrupoAdapter._GRUPO_KEY);
                //ELIMINAR EL GRUPO DE FIREBASE DATABASE
                databaseGrupos.child(grupoIdBorrar).removeValue();
                finish();
                break;
            case GrupoAdapter._KEY_EDITAR_GRUPO:
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

                break;

            //  AGREGAR UN MIEMBRO A UN GRUPO
            // En este caso habría que buscar la forma de validar el email del usuario ingresado
            case GrupoAdapter._KEY_INVITAR_USUARIO:
                grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);
                // findViews
                buttonAltaGrupo = (Button) findViewById(R.id.bAltaGrupo);
                buttonAltaGrupo.setText("Invitar Usuario");
                etNombreGrupo = (EditText) findViewById(R.id.etNombreGrupo);
                etNombreGrupo.setHint("Correo del usuario");


                buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ////////////////////////////////////////////////////////////
                        //  FALTA VALIDAR QUE EL EMAIL EXISTA EN BASE DE DATOS!!! //
                        ////////////////////////////////////////////////////////////
                        grupo.addidUsuariosInvitados(etNombreGrupo.getText().toString());
                        databaseGrupos.child(grupo.getGrupoId()).setValue(grupo);
                        finish();
                    }
                });

                break;
        }

    }



    // Insertar usuario en al base de datos Firebase
    private String createGrupoFirebase(String nombreGrupo) {
        String id = databaseGrupos.push().getKey();
        Grupo grupo = new Grupo();
        grupo.setGrupoId(id);
        grupo.setGrupoNombre(nombreGrupo);
        grupo.addidUsuariosInvitados(mAuth.getCurrentUser().getEmail());
        // Producto Hardcodeado //
        Producto productoHC = new Producto();
        productoHC.setProductoId("1");
        productoHC.setProductoNombre("Producto 1");
        productoHC.setProductoDescripcion("descripcionnnnnn");
        // Producto Hardcodeado //


        ArrayList<Producto> listaProductos = new ArrayList<>();
        //listaProductos.add(producto);
        grupo.setListaProductos(listaProductos);
        databaseGrupos.child(id).setValue(grupo);
        Toast.makeText(AbmcGrupo.this, "Grupo creado ",Toast.LENGTH_SHORT).show();
        return id;
    }
}