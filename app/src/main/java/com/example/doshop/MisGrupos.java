package com.example.doshop;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doshop.domain.Grupo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class MisGrupos extends AppCompatActivity {
    private final BroadcastReceiver br = new MyReceiver();
    public static final String CHANNEL_ID="99999";
    private RecyclerView mRecyclerView;
    private List<Grupo> listaDataSet;
    private RecyclerView.Adapter mAdapter;
    DatabaseReference databaseGrupos;
    private FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPrincipalCrearGrupo:
                Intent i1 = new Intent(MisGrupos.this, AbmcGrupo.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                i1.putExtra(GrupoAdapter._ABMC_GRUPO_MODO_KEY, GrupoAdapter._KEY_CREAR_GRUPO );
                startActivity(i1);
                return true;
            case R.id.menuSignOut:
                mAuth.signOut();
                finish();
                Intent i2 = new Intent (MisGrupos.this,LoginUsuario.class);
                startActivity(i2);
                return true;

            default:
                Toast.makeText(this, ". . . . ", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);

        // CLOUD MESSAGING: Se guarda el token en las preferencias en caso de que sea un  nuevo usuario
        getUserToken();

        //Inicializo la lista de grupos
        listaDataSet = new ArrayList<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get usuario autentificado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Usuario conectado
        String user = currentUser.getUid();

        databaseGrupos = FirebaseDatabase.getInstance().getReference("grupos");


        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMisGrupos);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(currentUser.getEmail());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        // RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.GruposRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MisGrupos.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //CREACION DEL CANAL DE NOTIFICACIONES
        this.createNotificationChannel();
        IntentFilter filtro = new IntentFilter();
        registerReceiver(br, filtro);


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseGrupos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDataSet.clear();
                String usuario = mAuth.getCurrentUser().getEmail();

                for(DataSnapshot grupoSnapshot: dataSnapshot.getChildren()){
                    for (DataSnapshot data: grupoSnapshot.getChildren()){
                        Grupo grupo = data.getValue(Grupo.class);

                        // CORROBORAR QUE EL USUARIO ES MIEMBRO DEL GRUPO
                        if(grupo.getUsuariosInvitados().contains(usuario)){
                            listaDataSet.add(grupo);
                        }
                        // MUESTRA TODOS LOS GRUPOS
                        //listaDataSet.add(grupo);
                    }
                }
                mAdapter = new GrupoAdapter( listaDataSet );
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private String getUserToken() {
        // RECUPERAR TOKEN O CREARLO
        String TOKEN = getTokenFromPrefs();
        if (TOKEN == null) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String newToken = instanceIdResult.getToken();
                            saveTokenToPrefs(newToken);
                            Log.e("newToken", newToken);
                        }
                    });
        }
        return TOKEN;
    }

    private void saveTokenToPrefs(String _token){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
    }
    private String getTokenFromPrefs(){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CANAL DO SHOP";
            String description = "Este canal esta creado para configurar las notificaciones de DO SHOP";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(br);
        super.onDestroy();
    }

}
