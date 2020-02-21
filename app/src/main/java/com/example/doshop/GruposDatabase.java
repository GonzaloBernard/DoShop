package com.example.doshop;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.doshop.domain.Grupo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GruposDatabase {

    private static GruposDatabase _GRUPOS_DATABASE = null;


    public static GruposDatabase getInstance(){
        if(_GRUPOS_DATABASE ==null) {
            _GRUPOS_DATABASE = new GruposDatabase();
        }
        return _GRUPOS_DATABASE;
    }
    public void insertarGrupo(Grupo grupo){
        // Get usuario autentificado
        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Abrir conexion con la base de datos
        DatabaseReference databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(usuarioId);

        String id = grupo.getGrupoId();
        if(id=="") id = databaseGrupos.push().getKey();

        grupo.setGrupoId(id);
        databaseGrupos.child(grupo.getGrupoId()).setValue(grupo).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void borrarGrupo(String grupo){
        // Get usuario autentificado
        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Abrir conexion con la base de datos
        DatabaseReference databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos").child(usuarioId);
        databaseGrupos.child(grupo).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
