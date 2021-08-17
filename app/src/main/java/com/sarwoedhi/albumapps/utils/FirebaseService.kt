package com.sarwoedhi.albumapps.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.text.TextUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.ui.main.MainActivity
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor

class FirebaseService : FirebaseMessagingService() {

    companion object {
        const val TAG = "FCMService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage!!.from!!)

        var messageTitle: String? = ""
        var messageBody: String? = ""
        val messageIcon = ""
        var messageUrl: String? = ""
        var isData = false

        // Check if message contains a data payload.
        if (remoteMessage.data != null && remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            isData = true
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            messageTitle = remoteMessage.notification!!.title
            messageBody = remoteMessage.notification!!.body

            Log.d(TAG, "Message Notification Title: " + messageTitle!!)
            Log.d(TAG, "Message Notification Body: " + messageBody!!)
        }

        //payload data
        if (isData) {
            if (messageTitle != null && messageTitle.length > 0 || messageBody != null && messageBody.length > 0) {
                //show message with payload
                messageUrl = remoteMessage.data["deeplink"]
                if ((messageUrl != null) and !TextUtils.isEmpty(messageUrl)) {
                    sendNotification(messageTitle, messageBody, messageUrl, remoteMessage.data)
                } else {
                    //show general message
                    sendNotification(messageTitle, messageBody, messageUrl)
                }
            }
        } else if (messageTitle != null && messageTitle.length > 0 || messageBody != null && messageBody.length > 0) {
            //show general message
            sendNotification(messageTitle, messageBody, "")
        }
    }

    private fun sendNotification(title: String?, messageBody: String?, url: String? = null, data: Map<String, String>? = null) {
        val intent = intentFor<MainActivity>().clearTop()//2. Clear Top — Clears all the previous views.
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        data?.keys?.forEach {
            intent.putExtra(it, data[it])
        }


        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "push-notification"
        val channelName = "Push Notification"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setColor(ContextCompat.getColor(applicationContext, R.color.design_default_color_primary))
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d(TAG, "refreshed token : " + s!!)
    }
}
