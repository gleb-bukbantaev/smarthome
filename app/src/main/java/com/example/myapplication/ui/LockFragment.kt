package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.LightState
import com.example.myapplication.data.LockState
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.coroutines.launch

class LockFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lock, container, false)
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.refresh)

        refresh.setOnRefreshListener {
            update()
            refresh.isRefreshing = false
        }
        return view
    }
    var lock: Boolean = false


    fun update() {
        lifecycleScope.launch {
            val state = WebClient.getLockState()
            lock = state.state
        }

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        update()
        on_off.setOnClickListener {
            try {
                lifecycleScope.launch {
                    WebClient.setLockState(
                        LockState(!lock)
                    )
                    update()
                }
            }catch (e:Exception){}
        }
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }
}
