package com.example.doshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doshop.domain.Grupo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AbmcGrupo extends AppCompatActivity {

    DatabaseReference databaseGrupos;
    private Button buttonAltaGrupo;
    private EditText etNombreGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abmc_grupo);

        // Referencia a la tabla grupos
        databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos");
        // findViews
        buttonAltaGrupo = (Button) findViewById(R.id.bAltaGrupo);
        etNombreGrupo = (EditText) findViewById(R.id.etNombreGrupo);

        buttonAltaGrupo.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Insertar grupo en Firebase database
                createGrupoFirebase(etNombreGrupo.getText().toString());
                // Retornar a MisGrupos
                finish();
            }
        });
    }



    // Insertar usuario en al base de datos Firebase
    private void createGrupoFirebase(String nombreGrupo) {
        String id = databaseGrupos.push().getKey();
        Grupo grupo = new Grupo();
        grupo.setGrupoId(id);
        grupo.setGrupoNombre(nombreGrupo);
        databaseGrupos.child(id).setValue(grupo);
        Toast.makeText(AbmcGrupo.this, "Grupo creado ",Toast.LENGTH_SHORT).show();
    }
}
