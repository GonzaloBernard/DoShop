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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        switch (extras.getInt(GrupoAdapter._ABMC_GRUPO_MODO_KEY)) {
            case GrupoAdapter._KEY_CREAR_GRUPO:
                try {
                    // findViews
                    buttonAltaGrupo = (Button) findViewById(R.id.bAltaGrupo);
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
                String grupoId = extras.getString(GrupoAdapter._GRUPO_KEY);
                //ELIMINAR EL GRUPO DE FIREBASE DATABASE
                databaseGrupos.child(grupoId).removeValue();
                finish();
                break;
        }

    }



    // Insertar usuario en al base de datos Firebase
    private String createGrupoFirebase(String nombreGrupo) {
        String id = databaseGrupos.push().getKey();
        Grupo grupo = new Grupo();
        grupo.setGrupoId(id);
        grupo.setGrupoNombre(nombreGrupo);
        databaseGrupos.child(id).setValue(grupo);
        Toast.makeText(AbmcGrupo.this, "Grupo creado ",Toast.LENGTH_SHORT).show();
        return id;
    }
}