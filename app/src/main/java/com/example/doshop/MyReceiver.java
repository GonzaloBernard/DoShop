package com.example.doshop;
/*
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {
    private Integer i=0;
    public String CHANNEL_ID = "99999";
    //ES UN ID AUTOINCREMENTAL PARA LAS NOTIFICACIONES
    private Integer ID_NOTIFICACION =0;
    @Override
    public void onReceive(Context context, Intent intentOrigen) {
        Toast.makeText(context, "Mensaje recibido", Toast.LENGTH_LONG).show();
        // LOGICA DEL INTENT PARA CONSULTAR EL PLATO
        Intent intent = new Intent(context.getApplicationContext(), MisGrupos.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //SE CREA EL PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);
        // LOGICA DE LA NOTIFICACION
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_lupa)
                .setContentTitle(intentOrigen.getStringExtra("titulo"))
                .setContentText(intentOrigen.getStringExtra("descripcion"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(ID_NOTIFICACION++, mBuilder.build());
    }
}
*/