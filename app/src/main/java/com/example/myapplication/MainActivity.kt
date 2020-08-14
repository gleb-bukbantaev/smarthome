package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.TokenRequest
import com.example.myapplication.ui.MenuFragment
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            if (FirebaseInstanceId.getInstance().token != null) {
                WebClient.setToken(TokenRequest(FirebaseInstanceId.getInstance().token!!))
            }
        }
        if (savedInstanceState == null) replace (MenuFragment())

    }
    fun replace(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    fun back() {
        supportFragmentManager.popBackStack()
    }

    fun add(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

}