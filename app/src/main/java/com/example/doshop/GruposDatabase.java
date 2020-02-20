package com.example.doshop;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doshop.domain.Grupo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GruposDatabase {

    private static GruposDatabase _GRUPOS_DATABASE = null;


    public static GruposDatabase getInstance(){
        if(_GRUPOS_DATABASE ==null) {
            _GRUPOS_DATABASE = new GruposDatabase();
        }
        return _GRUPOS_DATABASE;
    }
    public void insertGrupo(Grupo grupo){
        // Get usuario autentificado
        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Abrir conexion con la base de datos
        DatabaseReference databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(usuarioId);
        databaseGrupos.child(usuarioId).setValue(grupo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("ERROR :::","onCompleteListener task Successful ");
                }
                else {
                    Log.e("ERROR :::","onCompleteListener task unsuccessful ");
                }
            }
        });
    }
}
