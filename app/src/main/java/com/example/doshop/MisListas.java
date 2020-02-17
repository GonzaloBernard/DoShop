package com.example.doshop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisListas extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grupos, menu);
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
        setContentView(R.layout.activity_mis_listas);

        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMisListas);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "LISTAS DEL GRUPO:" );
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        final Button buttonCrearLista = (Button) findViewById(R.id.buttonCrearLista);
        mRecyclerView = (RecyclerView) findViewById(R.id.ListasRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MisListas.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

}
