package com.example.myapplication.ui

import WebClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.LockState
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
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
            on_off.isChecked = lock
        }

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope
            .launch {
                while (isActive){
                    update()
                    delay(5000)
                }
            }
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
