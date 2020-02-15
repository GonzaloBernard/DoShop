package com.example.doshop;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisGrupos extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);
        final Button buttonCrearGrupo = (Button) findViewById(R.id.buttonCrearGrupo);
        mRecyclerView = (RecyclerView) findViewById(R.id.GruposRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MisGrupos.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
