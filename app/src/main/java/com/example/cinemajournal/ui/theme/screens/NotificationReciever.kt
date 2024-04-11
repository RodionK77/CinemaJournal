package com.example.cinemajournal.ui.theme.screens

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import java.util.Calendar

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cinemajournal.R
import java.util.concurrent.atomic.AtomicInteger

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = NotificationManagerCompat.from(context)

        val title = intent.getStringExtra("title") ?: "Пора смотреть!"
        val message = intent.getStringExtra("message") ?: "Фильм"

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_journal)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val notificationId = generateUniqueNotificationId()

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        private const val CHANNEL_ID = "My_channel"

        private val notificationIdGenerator = AtomicInteger(0)

        private fun generateUniqueNotificationId(): Int {
            return notificationIdGenerator.incrementAndGet()
        }
    }
}
