package com.example.task.activity

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task.databinding.ActivityMainBinding
import com.example.task.english.EnglishActivity
import com.example.task.hindi.HindiActivity
import com.example.task.tamil.TamilActivity
import com.example.task.utilts.Service
import com.example.task.utilts.sharedperference.SharedPreferenceHelper


class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, Service::class.java))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        if (getButton() =="English"){
            startEnglish()
            finish()
        }else if (getButton() =="Tamil"){
            startTamil()
            finish()
        }else if (getButton() == "Hindi"){
            startHindi()
            finish()
        }

    }


    fun init(){
        binding.btnEnglish.setOnClickListener{
            saveButton("English")
            startEnglish()

        }
        binding.btnTamil.setOnClickListener {
            saveButton("Tamil")
            startTamil()
        }
        binding.btnHindi.setOnClickListener {
            saveButton("Hindi")
            startHindi()

        }
    }

    private fun saveButton(name:String){
        val sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this)
        sharedPreferenceHelper.add("name",name)
    }
    private fun getButton():String?{
        val sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this)
        return sharedPreferenceHelper.get("name")

    }

    fun startEnglish(){

            val intent = Intent(this, EnglishActivity::class.java)
            intent.putExtra("ENGLISH","English Post")
            startActivity(intent)
            finish()
    }
    fun startTamil(){

            val intent = Intent(this, TamilActivity::class.java)
            intent.putExtra("TAMIL","Tamil Post")
            startActivity(intent)
            finish()


    }
    fun startHindi(){
        val intent = Intent(this, HindiActivity::class.java)
        intent.putExtra("HINDI","Hindi Post")
        startActivity(intent)
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()

    }


}