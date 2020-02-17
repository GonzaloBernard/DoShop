package com.example.doshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView tvUsuarioLogeado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final Button buttonMisListas = (Button) findViewById(R.id.buttonMisListas);
        final Button buttonMisGrupos = (Button) findViewById(R.id.buttonMisGrupos);
        tvUsuarioLogeado = (TextView) findViewById(R.id.usuarioLogeado);


        buttonMisListas.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent i = new Intent(MainActivity.this, MisListas.class);
                   startActivity(i);
               }
        }
        );

        buttonMisGrupos.setOnClickListener(new Button.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MainActivity.this, MisGrupos.class);
               startActivity(i);

           }
       });

        // Get usuario logueado actualmente
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // Get usuario logueado actualmente
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSignOut:
                mAuth.signOut();
                finish();
                return true;

            default:
                Toast.makeText(this, ". . . . ", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            mAuth.signOut();
        }
        else {
            tvUsuarioLogeado.setText(currentUser.getEmail());
        }
    }
}
