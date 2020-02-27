package com.example.doshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doshop.repository.GruposDatabase;
import com.example.doshop.domain.Grupo;
import com.google.firebase.auth.FirebaseAuth;


public class AbmcGrupo extends AppCompatActivity {
    private Button buttonAltaGrupo;
    private Button bInvitarUsuario;
    private EditText etNombreGrupo;
    private EditText etInvitarUsuario;

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

        // findViews
        buttonAltaGrupo = (Button) findViewById(R.id.buttonAltaGrupo);
        etNombreGrupo = (EditText) findViewById(R.id.editTextNombreGrupo);
        bInvitarUsuario = (Button) findViewById(R.id.bInvitarUsuario);
        etInvitarUsuario = (EditText) findViewById(R.id.etInvitarUsuario);

        final Grupo grupo;
        switch (extras.getInt(GrupoAdapter._ABMC_GRUPO_MODO_KEY)) {

            case GrupoAdapter._KEY_CREAR_GRUPO:
                etInvitarUsuario.setVisibility(View.GONE);
                bInvitarUsuario.setVisibility(View.GONE);
                etNombreGrupo.setVisibility(View.VISIBLE);
                buttonAltaGrupo.setVisibility(View.VISIBLE);
                try {
                    buttonAltaGrupo.setText("Crear grupo");
                    buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Grupo grupo = new Grupo();
                            grupo.setGrupoId("");
                            grupo.setGrupoNombre(etNombreGrupo.getText().toString());
                            grupo.setGrupoAdmin(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            grupo.addUsuarioInvitado(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            CrearGrupoFirebase crearGrupoFirebase = new CrearGrupoFirebase();
                            crearGrupoFirebase.execute(grupo);
                            finish();
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(AbmcGrupo.this, "Error !!!",Toast.LENGTH_SHORT).show();
                    Log.e("Error ::::: ",e.getMessage());
                }
                break;

            case GrupoAdapter._KEY_EDITAR_GRUPO:
                etInvitarUsuario.setVisibility(View.GONE);
                bInvitarUsuario.setVisibility(View.GONE);
                etNombreGrupo.setVisibility(View.VISIBLE);
                buttonAltaGrupo.setVisibility(View.VISIBLE);

                try {
                    grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);
                    buttonAltaGrupo.setText("Editar grupo");
                    etNombreGrupo.setText(grupo.getGrupoNombre());
                    buttonAltaGrupo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            grupo.setGrupoNombre(etNombreGrupo.getText().toString());
                            CrearGrupoFirebase crearGrupoFirebase = new CrearGrupoFirebase();
                            crearGrupoFirebase.execute(grupo);
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
                BorrarGrupoFirebase borrarGrupoFirebase = new BorrarGrupoFirebase();
                borrarGrupoFirebase.execute(grupoIdBorrar);
                finish();
                break;

            case GrupoAdapter._KEY_INVITAR_USUARIO:
                etInvitarUsuario.setVisibility(View.VISIBLE);
                bInvitarUsuario.setVisibility(View.VISIBLE);
                etNombreGrupo.setVisibility(View.GONE);
                buttonAltaGrupo.setVisibility(View.GONE);

                grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);
                bInvitarUsuario.setText("Invitar Usuario");
                bInvitarUsuario.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ////////////////////////////////////////////////////////////
                        //  FALTA VALIDAR QUE EL EMAIL EXISTA EN BASE DE DATOS!!! //
                        ////////////////////////////////////////////////////////////
                        grupo.addUsuarioInvitado(etInvitarUsuario.getText().toString());

                        CrearGrupoFirebase crearGrupoFirebase = new CrearGrupoFirebase();
                        crearGrupoFirebase.execute(grupo);
                        finish();
                    }
                });
                break;
        }
    }



    // Insertar grupo en al base de datos Firebase
    class CrearGrupoFirebase extends AsyncTask<Grupo, Void, Void> {

        @Override
        protected Void doInBackground(Grupo... grupos) {
            GruposDatabase gruposDatabase = GruposDatabase.getInstance();
            gruposDatabase.insertarGrupo(grupos[0]);
            return null;
        }
    }

    // Borrar grupo en al base de datos Firebase
    class BorrarGrupoFirebase extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... grupos) {
            GruposDatabase gruposDatabase = GruposDatabase.getInstance();
            gruposDatabase.borrarGrupo(grupos[0]);
            return null;
        }
    }
}