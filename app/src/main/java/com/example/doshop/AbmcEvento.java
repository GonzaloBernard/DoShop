package com.example.doshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doshop.domain.Evento;
import com.example.doshop.domain.Grupo;
import com.example.doshop.repository.GruposDatabase;

public class AbmcEvento extends AppCompatActivity {
    private Button bEventoCrear;
    private EditText etEventoNombre;
    private Spinner sEventoTipo;
    private Spinner sEventoPrioridad;

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

        final Bundle extras = getIntent().getExtras();

        // FINDVIEWS
        etEventoNombre = (EditText) findViewById(R.id.editTextEventoNombre);
        bEventoCrear = (Button) findViewById(R.id.bEventoCrear);
        sEventoTipo = (Spinner) findViewById(R.id.spinnerEventoTipo);
        sEventoPrioridad = (Spinner) findViewById(R.id.sEventoPrioridad);

        switch (extras.getInt(GrupoAdapter._ABMC_EVENTO_MODO_KEY)) {
            case GrupoAdapter._KEY_CREAR_EVENTO:
                sEventoPrioridad.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, new String[]{"Normal","Urgente"}));
                sEventoTipo.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, new String[]{"Compra","Otro"}));

                bEventoCrear.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Grupo grupo = extras.getParcelable(GrupoAdapter._GRUPO_KEY);
                        Evento evento = new Evento();
                        evento.setEventoId(String.valueOf(grupo.getListaEventos().size()));
                        evento.setEventoNombre(etEventoNombre.getText().toString());
                        grupo.addEvento(evento);
                        AbmcEvento.CrearGrupoFirebase crearGrupoFirebase = new AbmcEvento.CrearGrupoFirebase();
                        crearGrupoFirebase.execute(grupo);
                        finish();
                    }
                });
                break;
            case GrupoAdapter._KEY_BORRAR_EVENTO:
                break;
            case GrupoAdapter._KEY_EDITAR_EVENTO:
                break;
        }
    }


    // Insertar grupo en la base de datos Firebase
    class CrearGrupoFirebase extends AsyncTask<Grupo, Void, Void> {
        @Override
        protected Void doInBackground(Grupo... grupos) {
            GruposDatabase gruposDatabase = GruposDatabase.getInstance();
            gruposDatabase.insertarGrupo(grupos[0]);
            return null;
        }
    }
}