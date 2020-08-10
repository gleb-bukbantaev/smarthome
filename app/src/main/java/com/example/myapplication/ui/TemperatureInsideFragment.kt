package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.LightState
import com.example.myapplication.data.TemperatureInsideState
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.android.synthetic.main.fragment_light.back
import kotlinx.android.synthetic.main.fragment_temperature_inside.*
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_light.main as main1

class TemperatureInsideFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_temperature_inside, container, false)
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.refresh)
        refresh.setOnRefreshListener {
            update()
            refresh.isRefreshing = false
        }
        return view

    }

    var mode: Boolean = false
    var heater: Boolean = false
    var window: Boolean = false
    var minLevel: Int = 0
    var maxLevel: Int = 0
    var nowlevel: Int = 0

    fun update() {
        val main = view!!.findViewById<Button>(R.id.main)
        lifecycleScope.launch {
            val state = WebClient.getTemperatureInsideState()
            mode = state.mode
            heater = state.heaterState
            window = state.windowState
            minLevel = state.low
            maxLevel = state.top
            nowlevel = state.value
            main.text = "$nowlevel C*"
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        update()
        heater_on_off.setOnClickListener {
            try {
                lifecycleScope.launch {
                    WebClient.setTemperatureInsideState(
                        TemperatureInsideState(mode, !heater, window, minLevel, maxLevel, 0)
                    )
                    update()
                }
            } catch (e: Exception) {
            }
        }
        window_on_off.setOnClickListener {
            try {
                lifecycleScope.launch {
                    WebClient.setTemperatureInsideState(
                        TemperatureInsideState(mode, heater, !window, minLevel, maxLevel, 0)
                    )
                    update()
                }
            } catch (e: Exception) {
            }
        }
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }


}