package com.example.myapplication

import WebClient
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.request.GetRequest
import com.example.myapplication.data.LockState
import kotlinx.android.synthetic.main.activity_door.*
import kotlinx.coroutines.launch

class DoorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door)
        val photo = intent.getStringExtra("photo")
        lifecycleScope.launch {
            cameraView.setImageBitmap(photo?.let { getBitmap(it) })
        }
        lock.setOnClickListener {

            try {
                lifecycleScope.launch {
                    WebClient.setLockState(
                        LockState(true)
                    )

                }
            } catch (e: Exception) {
            }
        }
    }

    suspend fun getBitmap(url: String): Bitmap? {
        val request = GetRequest.Builder(this)
            .data(url)
            .build()
        val result = Coil.imageLoader(this).execute(request).drawable
        return result?.toBitmap(result.intrinsicWidth, result.intrinsicHeight)
    }

}