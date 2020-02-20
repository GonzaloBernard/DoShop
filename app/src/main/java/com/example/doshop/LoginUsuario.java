package com.example.doshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doshop.domain.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUsuario extends AppCompatActivity {

    private static final String TAG = "LoginUsuario";
    private FirebaseAuth mAuth;
    private Button loginButton;
    private Button signinButton;
    private EditText mEmailField;
    private EditText mPasswordField;

    DatabaseReference databaseUsers;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSignOut:
                finish();
                return true;
            default:
                Toast.makeText(this, ". . . . ", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            // Se quita la flecha de regreso en el la pantalla de LogIn
            // ---> Reempazado por  opcion de menu: Salir
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
            actionBar.setTitle("DoShop");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Referencia a la tabla usuarios
        databaseUsers = FirebaseDatabase.getInstance().getReference("usuarios");


        mEmailField = (EditText) findViewById(R.id.Email);
        mPasswordField = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        signinButton = (Button) findViewById(R.id.signin);


        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                }
            }
        });

        signinButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()) {
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                }
            }
        });

    }

    private void createAccount(final String email, String password) {

        // [START create_user_with_email]
        Task<AuthResult> mAuthTask = mAuth.createUserWithEmailAndPassword(email, password);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                // Guardar usuario en base de datos
                                createUserFirebase(email);
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(LoginUsuario.this, MisGrupos.class);
                                startActivity(i);
                                finish();
                            }
                            catch (Exception e){
                                Log.w(TAG, e.getMessage(), task.getException());
                                Toast.makeText(LoginUsuario.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginUsuario.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    // Insertar usuario en al base de datos Firebase
    private void createUserFirebase(String userEmail) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Usuario conectado
        String userUid = currentUser.getUid();
        // Crear id para el nuevo usuario
        String id = databaseUsers.push().getKey();
        // Crear el objeto usuario con sus atributos
        Usuario usuario = new Usuario();
        usuario.setUserId(id);
        usuario.setUserName(userEmail);
        usuario.setUserUid(userUid);
        // Guardar el usuario creado
        databaseUsers.child(id).setValue(usuario);
        Toast.makeText(LoginUsuario.this, "Usuario Firebase creado ",Toast.LENGTH_SHORT).show();
    }
    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(LoginUsuario.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginUsuario.this, MisGrupos.class);
                                startActivity(i);
                                finish();
                            }
                            catch (Exception e){
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, e.getMessage(), task.getException());
                                Toast.makeText(LoginUsuario.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginUsuario.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void handlerErrors(Error error){


    }


    // Validaciones para los datos de entrada
    private boolean validateForm() {
        boolean valid = true;
        String correo = mEmailField.getText().toString();
        int passw = mPasswordField.getText().toString().length();
        try{
            // Aca van todoss los datos de entrada
            //if (correo.length()<6 || correo.length() > 60) mEmailField.setError("No se corresponde a un correo válido");
            //if (passw < 6) mPasswordField.setError("La contraseña es incorrecta");
            if (correo.length()<6 || correo.length() > 60) throw new Exception("Invalid Email");
            if (passw <6) throw new Exception("Invalid Password");
        }
        catch (Exception e){
            valid=false;
            Toast.makeText(LoginUsuario.this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    // No se destruye la activity
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}