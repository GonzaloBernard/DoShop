package com.example.doshop;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisListas extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_listas);

        final Button buttonCrearLista = (Button) findViewById(R.id.buttonCrearLista);
        mRecyclerView = (RecyclerView) findViewById(R.id.ListasRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MisListas.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

}
