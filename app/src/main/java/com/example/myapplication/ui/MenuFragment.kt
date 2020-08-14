package com.example.myapplication.ui

import WebClient
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        return view
    }


    var lightState: Boolean = false
    var lightValue: Int = 0
    var humidityState: Boolean = false
    var humidityValue: Int = 0
    var heaterState: Boolean = false
    var temperatureInsideValue: Int = 0
    var temperatureOutsideValue: Int = 0
    var pressureValue: Int = 0
    var windowState: Boolean = false
    var lockState: Boolean = false

    fun update() {

        lifecycleScope.launch {
            val state = WebClient.getMenuState()
            lightState = state.lightState
            lightValue = state.lightValue
            humidityState = state.humidityState
            humidityValue = state.humidityValue
            heaterState = state.heaterState
            temperatureInsideValue = state.temperatureInsideValue
            temperatureOutsideValue = state.temperatureOutsideValue
            pressureValue = state.pressureValue
            windowState = state.windowState
            lockState = state.lockState
            come_light.imageTintList = if (lightState) {
                ColorStateList.valueOf(Color.parseColor("#ffff00"))
            } else {
                ColorStateList.valueOf(Color.parseColor("#808080"))
            }
            come_humidity.imageTintList = if (humidityState) {
                ColorStateList.valueOf(Color.parseColor("#6495ed"))
            } else {
                ColorStateList.valueOf(Color.parseColor("#808080"))
            }
            pressure.text = "$pressureValue"
            temperatureOutside.text = "$temperatureOutsideValue"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope
            .launch {
                while (isActive) {
                    update()
                    delay(5000)
                }
            }
        come_light.setOnClickListener {
            (activity as? MainActivity)?.add(LightFragment())
        }
        come_humidity.setOnClickListener {
            (activity as? MainActivity)?.add(HumidityFragment())
        }
        come_temperature.setOnClickListener {
            (activity as? MainActivity)?.add(TemperatureInsideFragment())
        }
        come_lock.setOnClickListener {
            (activity as? MainActivity)?.add(LockFragment())
        }
        come_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryFragment())
        }

    }

}
