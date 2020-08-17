package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.HistoryGraph
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.back

class HistoryFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        come_humidity_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryGraphFragment(HistoryGraph.HUMIDITY))
        }
        come_temperature_inside_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryGraphFragment(HistoryGraph.TEMPERATURE_INSIDE))
        }
        come_temperature_outside_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryGraphFragment(HistoryGraph.TEMPERATURE_OUTSIDE))
        }
        come_power_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryGraphFragment(HistoryGraph.POWER))
        }
        come_pressure_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryGraphFragment(HistoryGraph.PRESSURE))
        }
        come_co2_history.setOnClickListener {
            (activity as? MainActivity)?.add(HistoryGraphFragment(HistoryGraph.CO2))
        }
        come_lock_history.setOnClickListener {
            (activity as? MainActivity)?.add(LockHistoryFragment())
        }
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }
}