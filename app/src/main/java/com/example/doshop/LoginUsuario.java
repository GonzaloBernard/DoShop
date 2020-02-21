package com.example.doshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        mEmailField.setError(null);
        mPasswordField.setError(null);
        mEmailField.addTextChangedListener(loginTextWatcher);
        mPasswordField.addTextChangedListener(loginTextWatcher);

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

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = mEmailField.getText().toString().trim();
            String passwordInput = mPasswordField.getText().toString().trim();
            loginButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            signinButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void createAccount(final String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        handlerErrors(0,task);
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
                                //Toast.makeText(LoginUsuario.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(LoginUsuario.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
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
    private void signIn(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        handlerErrors(1,task);
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
                                //Toast.makeText(LoginUsuario.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginUsuario.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    //Seguridad de autenticacion y manejo de la misma
    public void handlerErrors(final int btn, final Task<AuthResult> task){
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean exists = false; //flag para comprobar existencia de usuario
                String mail = mEmailField.getText().toString();
                try{
                    if(validateForm()) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Usuario user = snapshot.getValue(Usuario.class); //obtengo una instancia x de la bd
                            if (user.getUserName().equals(mail)) {
                                exists = true;
                                break;
                            } else {
                                exists = false;
                            }
                            //String uid = snapshot.getKey();
                        }
                        //Aqui se pueden agregar mas errores que puedan llegar a suceder
                        if (exists && btn==0){
                            if(!task.isSuccessful())mEmailField.setError("Debe utilizar otro correo electrónico");
                        }else if(exists && btn==1){
                            if(!task.isSuccessful()) mPasswordField.setError("La contraseña es incorrecta");
                            else throw new Exception("Autenticacón exitosa");
                        }else if(!exists && btn ==1){
                            throw new Exception("Debe primero crear el usuario");
                        }else{
                            mEmailField.setError("Este no es un correo válido");
                            throw new Exception("Los campos no son correctos");
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(LoginUsuario.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }



    // Validaciones para los datos de entrada
    private boolean validateForm() {
        boolean valid = true;
        String correo = mEmailField.getText().toString();
        int passw = mPasswordField.getText().toString().length();
        try{
            if (correo.length()<6 || correo.length() > 60){
                mEmailField.setError("No se corresponde con un correo válido");
                throw new Exception("Invalid Email");
            }
            if (passw <6){
                mPasswordField.setError("La contraseña debe tener mas de 6 carácteres");
                throw new Exception("Invalid Password");
            }
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