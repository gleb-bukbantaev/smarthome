package com.example.myapplication.ui

import WebClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.ChangeRange
import com.example.myapplication.data.HumidityState
import com.example.myapplication.data.LightState
import com.example.myapplication.data.TemperatureInsideState
import kotlinx.android.synthetic.main.fragment_range.*
import kotlinx.coroutines.launch

class RangeFragment(val range: ChangeRange): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_range, container, false)
        return view
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val topPicker = view.findViewById<NumberPicker>(R.id.topPicker)
        val lowPicker = view.findViewById<NumberPicker>(R.id.lowPicker)
        topPicker.minValue = 0
        topPicker.maxValue = 100
        lowPicker.minValue = 0
        lowPicker.maxValue = 100
        when (range){
            ChangeRange.LIGHT ->
                try {
                    lifecycleScope.launch {
                        val state = WebClient.getLightState()
                        lowPicker.value = state.low
                        topPicker.value = state.top
                    }
                } catch (e: Exception) {
                }
            ChangeRange.HUMIDITY ->
                try {
                    lifecycleScope.launch {
                        val state = WebClient.getHumidityState()
                        lowPicker.value = state.low
                        topPicker.value = state.top
                    }
                } catch (e: Exception) {
                }
            ChangeRange.TEMPERATURE ->
                try {
                    lifecycleScope.launch {
                        val state = WebClient.getTemperatureInsideState()
                        lowPicker.value = state.low
                        topPicker.value = state.top
                    }
                } catch (e: Exception) {
                }
        }
        accept.setOnClickListener {

            when (range) {
                ChangeRange.LIGHT ->
                    try {
                        lifecycleScope.launch {
                            val state = WebClient.getLightState()
                            WebClient.setLightState(
                                LightState(state.state, topPicker.value, lowPicker.value, 0)
                            )
                            (activity as? MainActivity)?.back()
                        }
                    } catch (e: Exception) {
                    }

                ChangeRange.HUMIDITY ->
                    try {

                        lifecycleScope.launch {
                            val state = WebClient.getHumidityState()
                            WebClient.setHumidityState(
                                HumidityState(state.mode, state.state, topPicker.value, lowPicker.value,
                                    0
                                )
                            )
                            (activity as? MainActivity)?.back()
                        }
                    } catch (e: Exception) {
                    }
                ChangeRange.TEMPERATURE ->
                    try {

                        lifecycleScope.launch {
                            val state = WebClient.getTemperatureInsideState()
                            WebClient.setTemperatureInsideState(
                                TemperatureInsideState(state.mode, state.heaterState, state.windowState, topPicker.value, lowPicker.value,
                                    0
                                )
                            )
                            (activity as? MainActivity)?.back()
                        }
                    } catch (e: Exception) {
                    }
            }
        }
    }

}
