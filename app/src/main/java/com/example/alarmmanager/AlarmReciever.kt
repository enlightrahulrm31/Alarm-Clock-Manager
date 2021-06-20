package com.example.alarmmanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmmanager.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_destination.*

class AlarmReciever:BroadcastReceiver() {
    lateinit var notification : Uri
    lateinit var r: Ringtone
    override fun onReceive(context: Context?, intent: Intent?) {
        val i =Intent(context,DestinationActivity::class.java)
        intent!!.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r= RingtoneManager.getRingtone(context, notification);
        val pendingintent = PendingIntent.getActivity(context,11,i,0)
        val builder = NotificationCompat.Builder(context!!,"AlarmManager")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("ALARM MANAGER")
            .setContentTitle("WAKE UP ALARM IS RINGING ")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingintent)

        val notificationManager = NotificationManagerCompat.from(context)
        r.play()
        notificationManager.notify(123,builder.build())

    }
}