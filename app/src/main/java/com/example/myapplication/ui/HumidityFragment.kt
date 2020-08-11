package com.example.myapplication.ui

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.HumidityState
import com.example.myapplication.data.LightState
import kotlinx.android.synthetic.main.fragment_humidity.*
import kotlinx.android.synthetic.main.fragment_humidity.back
import kotlinx.android.synthetic.main.fragment_humidity.main
import kotlinx.android.synthetic.main.fragment_humidity.on_off
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.coroutines.launch

class HumidityFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_humidity, container, false)
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.refresh)
        refresh.setOnRefreshListener {
            update()
            refresh.isRefreshing = false
        }
        return view
    }
    var mode: Boolean = false
    var humidity: Boolean = false
    var minLevel: Int = 0
    var maxLevel: Int = 0
    var nowlevel: Int = 0

    fun update() {
        val modeChange = view!!.findViewById<ToggleButton>(R.id.auto)
        lifecycleScope.launch {
            val state = WebClient.getHumidityState()
            mode = state.mode
            humidity = state.state
            minLevel = state.top
            maxLevel = state.low
            nowlevel = state.value
            main.text = "$nowlevel %"
            modeChange.isChecked = mode
            on_off.isChecked = humidity

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        update()
        on_off.setOnClickListener {
            try {
                lifecycleScope.launch {
                    WebClient.setHumidityState(
                        HumidityState(mode, !humidity, minLevel, maxLevel, 0)
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