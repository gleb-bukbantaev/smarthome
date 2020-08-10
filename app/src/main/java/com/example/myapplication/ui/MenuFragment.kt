package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.launch
import com.example.myapplication.MainActivity

class MenuFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.refresh_menu)
        refresh.setOnRefreshListener {
            update()
            refresh.isRefreshing = false
        }
        return view
    }


    var lightState: Boolean = false
    var lightValue: Int = 0
    var humidityState: Boolean = false
    var humidityValue: Int = 0
    var temperatureInsideState: Boolean = false
    var temperatureInsideValue: Int = 0
    var temperatureOutsideValue: Int = 0
    var pressureValue: Int = 0
    var windowState: Boolean = false
    var lockState: Boolean = false

    fun update() {
        val pressure = view!!.findViewById<Button>(R.id.pressure)
        val temperatureOutside = view!!.findViewById<Button>(R.id.temperatureOutside)
        val light = view!!.findViewById<Button>(R.id.come_light)
        val humidity = view!!.findViewById<Button>(R.id.come_humidity)
        val lock = view!!.findViewById<Button>(R.id.come_lock)
        val temperature = view!!.findViewById<Button>(R.id.come_temperature)
        val history = view!!.findViewById<Button>(R.id.come_history)
        lifecycleScope.launch {
            val state = WebClient.getMenuState()
            lightState = state.lightState
            lightValue = state.lightValue
            humidityState = state.humidityState
            humidityValue = state.humidityValue
            temperatureInsideState = state.temperatureInsideState
            temperatureInsideValue = state.temperatureInsideValue
            temperatureOutsideValue = state.temperatureOutsideValue
            pressureValue = state.pressureValue
            windowState = state.windowState
            lockState = state.lockState
            light.text = "$lightState | $lightValue"
            humidity.text = "$humidityState | $humidityValue"
            lock.text = "$lockState"
            pressure.text = "$pressureValue"
            temperatureOutside.text = "$temperatureOutsideValue"
            temperature.text = "$temperatureInsideState | $temperatureInsideValue"

        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        update()
        come_light.setOnClickListener {
            /**/
        }
        come_humidity.setOnClickListener {
            /**/
        }
        come_temperature.setOnClickListener {
            /**/
        }
        come_lock.setOnClickListener {
            /**/
        }
        come_history.setOnClickListener {
            /**/
        }

    }

}