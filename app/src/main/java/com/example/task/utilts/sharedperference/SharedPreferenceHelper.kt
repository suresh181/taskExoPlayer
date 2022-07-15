package com.example.task.utilts.sharedperference

import android.annotation.SuppressLint
import android.content.Context

class SharedPreferenceHelper(val context: Context) {
    fun add(key:String,value:String){
        context.getSharedPreferences( SHARE_PREF_FILE_NAME,Context.MODE_PRIVATE).edit().putString(key,value).apply()
    }

    fun get(key: String): String?{
        return context.getSharedPreferences(SHARE_PREF_FILE_NAME,Context.MODE_PRIVATE).getString(key,null)
    }

    companion object{
        private const val SHARE_PREF_FILE_NAME = "Shared_Pref"
        @SuppressLint("StaticFieldLeak")
        @Volatile private  var INSTANCE: SharedPreferenceHelper?=null
        fun getInstance(context: Context): SharedPreferenceHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPreferenceHelper(context)
            }


    }
}