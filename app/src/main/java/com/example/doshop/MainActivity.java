package com.example.doshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonMisListas = (Button) findViewById(R.id.buttonMisListas);
        final Button buttonMisGrupos = (Button) findViewById(R.id.buttonMisGrupos);

        buttonMisListas.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent i1= new Intent(MainActivity.this, MisListas.class);
               }
        }
        );

        buttonMisGrupos.setOnClickListener(new Button.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i2 = new Intent(MainActivity.this, MisGrupos.class);

           }
       });
    }
}
