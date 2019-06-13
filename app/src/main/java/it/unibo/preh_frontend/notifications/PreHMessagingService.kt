package it.unibo.preh_frontend.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import it.unibo.preh_frontend.MainActivity

class PreHMessagingService : FirebaseMessagingService() {

    val TAG = "PreH-Service"

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.i(TAG, "token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage!!.from)
        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "PreHNotifiche"
            val descriptionText = "Notifiche del PreH"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("provaprogetto.unibo.it.prova.ANDROID", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, "provaprogetto.unibo.it.prova.ANDROID")
                    .setContentText(remoteMessage.notification!!.body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }

        val intent1 = Intent(this@PreHMessagingService, MainActivity::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent1.putExtra("message", remoteMessage.notification!!.body!!)
        startActivity(intent1)
    }
}
