package com.example.task.utilts

import android.app.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.task.R
import com.example.task.activity.MainActivity
import com.example.task.activity.SplashActivity


class Service:Service() {
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Opening Task Activity"
    var not: SplashActivity? = null
    fun UpdaterServiceManager() {
        not = SplashActivity()
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        notificationManager.cancel(12345)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            notificationChannel = NotificationChannel(channelId, description, NotificationManager .IMPORTANCE_LOW)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel!!.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this,channelId)
                .setContentTitle("Task App " +
                        "Starting").setContentText("You are using the Test App").setSmallIcon(R.drawable.img)
            builder.setAutoCancel(true)

            builder.setContentIntent(pendingIntent)

        notificationManager= this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(12345, builder.build())
        startForeground(12345,builder.build())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }



    fun clearNotifications(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }
}