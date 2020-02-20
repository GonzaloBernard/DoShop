package com.example.doshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doshop.domain.Grupo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AbmcGrupo extends AppCompatActivity {

    DatabaseReference databaseGrupos;
    private Button buttonAltaGrupo;
    private EditText etNombreGrupo;
    private FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abmc_grupo, menu);
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
        setContentView(R.layout.activity_abmc_grupo);

        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.tbAbmcGrupo);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "GESTIONAR GRUPO" );
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Bundle extras = getIntent().getExtras();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        String user = mAuth.getCurrentUser().getUid();

        // Referencia a la tabla grupos
        databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(user);
        final Grupo grupo;
        switch (extras.getInt(GrupoAdapter._ABMC_GRUPO_MODO_KEY)) {
            case GrupoAdapter._KEY_CREAR_GRUPO:
                try {
                    // findViews
                    buttonAltaGrupo = (Button) findViewById(R.id.buttonAltaGrupo);
                    buttonAltaGrupo.setText("Crear grupo");
                    etNombreGrupo = (EditText) findViewById(R.id.editTextNombreGrupo);
                    buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Insertar grupo en Firebase database
                            String id = databaseGrupos.push().getKey();
                            Grupo grupo = new Grupo();
                            grupo.setGrupoId(id);
                            grupo.setGrupoNombre(etNombreGrupo.getText().toString());
                            grupo.setGrupoAdmin(mAuth.getCurrentUser().getEmail());
                            grupo.addUsuarioInvitado(mAuth.getCurrentUser().getEmail());

                            CrearGrupoFirebase crearGrupoFirebase = new CrearGrupoFirebase();
                            crearGrupoFirebase.execute(grupo);
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
                buttonAltaGrupo = (Button) findViewById(R.id.buttonAltaGrupo);
                buttonAltaGrupo.setText("Editar grupo");
                etNombreGrupo = (EditText) findViewById(R.id.editTextNombreGrupo);
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
            case GrupoAdapter._KEY_INVITAR_USUARIO:
                //  AGREGAR UN MIEMBRO A UN GRUPO
                // En este caso habría que buscar la forma de validar el email del usuario ingresado
                grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);
                // findViews
                buttonAltaGrupo = (Button) findViewById(R.id.buttonAltaGrupo);
                buttonAltaGrupo.setText("Invitar Usuario");
                etNombreGrupo = (EditText) findViewById(R.id.editTextNombreGrupo);
                // REUTILIZO EL EditText nombre del grupo
                etNombreGrupo.setHint("Correo del usuario");
                etNombreGrupo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


                buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ////////////////////////////////////////////////////////////
                        //  FALTA VALIDAR QUE EL EMAIL EXISTA EN BASE DE DATOS!!! //
                        ////////////////////////////////////////////////////////////
                        grupo.addUsuarioInvitado(etNombreGrupo.getText().toString());
                        databaseGrupos.child(grupo.getGrupoId()).setValue(grupo);
                        finish();
                    }
                });

                break;
        }

    }



    // Insertar usuario en al base de datos Firebase
    class CrearGrupoFirebase extends AsyncTask<Grupo, Void, Void> {

        @Override
        protected Void doInBackground(Grupo... grupos) {
            GruposDatabase gruposDatabase = GruposDatabase.getInstance();
            gruposDatabase.insertGrupo(grupos[0]);
            return null;
        }
    }
    private void crearGrupo(String nombreGrupo) {


    }
}