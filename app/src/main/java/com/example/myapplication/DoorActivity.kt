package com.example.myapplication

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DoorActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door)
        val photo = intent.getStringExtra("photo") 
    }
    fun getDoorContentIntent(photo: String): PendingIntent {
        val intent = Intent(this, DoorActivity::class.java)
        intent.putExtra("photo", photo)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}