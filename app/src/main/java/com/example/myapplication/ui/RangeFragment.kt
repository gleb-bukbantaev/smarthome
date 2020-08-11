package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_light.*
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
        accept.setOnClickListener {
            when (range){
                ChangeRange.LIGHT ->
                    try {

                        lifecycleScope.launch {
                            val state = WebClient.getLightState()
                            WebClient.setLightState(
                                LightState(state.state, lowPicker.value, topPicker.value, 0)
                            )

                        }
                    } catch (e: Exception) {}

                ChangeRange.HUMIDITY ->
                    try {

                        lifecycleScope.launch {
                            val state = WebClient.getHumidityState()
                            WebClient.setHumidityState(
                                HumidityState(state.mode, state.state, lowPicker.value, topPicker.value, 0)
                            )

                        }
                    } catch (e: Exception) {}
                ChangeRange.TEMPERATURE ->
                    try {

                    lifecycleScope.launch {
                        val state = WebClient.getTemperatureInsideState()
                        WebClient.setTemperatureInsideState(
                            TemperatureInsideState(state.mode, state.heaterState, state.windowState, lowPicker.value, topPicker.value, 0)
                        )

                    }
                } catch (e: Exception) {}
            }
            (activity as? MainActivity)?.back()
        }
    }

}
