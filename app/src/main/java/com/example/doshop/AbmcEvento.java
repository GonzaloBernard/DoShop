package com.example.doshop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AbmcEvento extends AppCompatActivity {

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

        Bundle extras = getIntent().getExtras();

        // FINDVIEWS

        switch (extras.getInt(GrupoAdapter._ABMC_GRUPO_MODO_KEY)) {
            case GrupoAdapter._KEY_CREAR_EVENTO:
                break;
            case GrupoAdapter._KEY_BORRAR_EVENTO:
                break;
            case GrupoAdapter._KEY_EDITAR_EVENTO:
                break;
        }
    }
}