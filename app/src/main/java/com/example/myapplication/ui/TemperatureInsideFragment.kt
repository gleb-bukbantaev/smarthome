package com.example.myapplication.ui

import WebClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.ChangeRange
import com.example.myapplication.data.TemperatureInsideState
import kotlinx.android.synthetic.main.fragment_light.back
import kotlinx.android.synthetic.main.fragment_temperature_inside.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class TemperatureInsideFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_temperature_inside, container, false)
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
        val modeChange = view!!.findViewById<ToggleButton>(R.id.auto)
        lifecycleScope.launch {
            val state = WebClient.getTemperatureInsideState()
            mode = state.mode
            heater = state.heaterState
            window = state.windowState
            minLevel = state.low
            maxLevel = state.top
            nowlevel = state.value
            main.text = "$nowlevel C*"
            modeChange.isChecked = mode
            heater_on_off.isChecked = heater
            window_on_off.isChecked = window
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
        heater_on_off.setOnClickListener {
            try {
                lifecycleScope.launch {
                    WebClient.setTemperatureInsideState(
                        TemperatureInsideState(mode, !heater, window, maxLevel, minLevel, 0)
                    )
                    update()
                }
            } catch (e: Exception) {
            }
        }
        main.setOnClickListener {
            (activity as? MainActivity)?.add(RangeFragment(ChangeRange.TEMPERATURE))
        }
        window_on_off.setOnClickListener {
            try {
                lifecycleScope.launch {
                    WebClient.setTemperatureInsideState(
                        TemperatureInsideState(mode, heater, !window, maxLevel, minLevel, 0)
                    )
                    update()
                }
            } catch (e: Exception) {
            }
        }
        auto.setOnCheckedChangeListener { compoundButton, b ->
            heater_on_off.isEnabled = !b
            window_on_off.isEnabled = !b
            lifecycleScope.launch { WebClient.setTemperatureInsideState(
                TemperatureInsideState(b, heater, window, maxLevel, minLevel, 0)
            ) }
        }
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }


}