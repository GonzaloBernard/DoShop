package com.example.doshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private TextView tvUsuarioLogeado;
    private Button loginButton;
    private Button signinButton;
    private Button signoutButton;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button bHomeActivity;

    DatabaseReference databaseUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("usuarios");


        mEmailField = (EditText) findViewById(R.id.username);
        mPasswordField = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        signinButton = (Button) findViewById(R.id.signin);
        signoutButton = (Button) findViewById(R.id.signout);
        tvUsuarioLogeado = (TextView) findViewById(R.id.usuarioLogeado);
        bHomeActivity = (Button) findViewById(R.id.bHomeActivity);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }

        });

        signinButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

            }
        });
        signoutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        bHomeActivity.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginUsuario.this, MainActivity.class);
                startActivity(i);
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginUsuario.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }
    // Insertar usuario en al base de datos Firebase
    private void createUserFirebase(String userEmail) {
        String id = databaseUsers.push().getKey();
        Usuario usuario = new Usuario();
        usuario.setUserId(id);
        usuario.setUserName(userEmail);
        databaseUsers.child(id).setValue(usuario);
        Toast.makeText(LoginUsuario.this, "Usuario Firebase creado ",Toast.LENGTH_SHORT).show();
    }
    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginUsuario.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            // Guardar usuario en base de datos
                            createUserFirebase(mEmailField.getText().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginUsuario.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });

    }

    private void signOut() {
        mAuth.signOut();
        Toast.makeText(LoginUsuario.this, "Sign out successfully",Toast.LENGTH_SHORT).show();
        updateUI(null);
    }

    // Validaciones para los datos de entrada
    private boolean validateForm() {
        boolean valid = true;
        return valid;
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Log.i("Login id:",user.getEmail());
            tvUsuarioLogeado.setText(user.getEmail());
            signoutButton.setVisibility(View.VISIBLE);
            bHomeActivity.setVisibility(View.VISIBLE);
            signinButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            mEmailField.setVisibility(View.GONE);
            mPasswordField.setVisibility(View.GONE);

        } else {
            Log.i("Login id:","Nadie conectado");
            tvUsuarioLogeado.setText("Usuario desconectado");
            signoutButton.setVisibility(View.GONE);
            bHomeActivity.setVisibility(View.GONE);
            signinButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            mEmailField.setVisibility(View.VISIBLE);
            mPasswordField.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {

    }


}