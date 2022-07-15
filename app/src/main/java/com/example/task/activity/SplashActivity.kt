package com.example.task.activity

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.task.R
import com.example.task.utilts.Service
import okhttp3.internal.notify
import java.util.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        startService(Intent(this,Service::class.java))

        callNextActivity()
    }

    private fun callNextActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
        }, 1000)
    }



    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    }
