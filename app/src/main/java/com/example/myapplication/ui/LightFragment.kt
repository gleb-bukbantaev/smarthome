package com.example.myapplication.ui

import WebClient
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.ChangeRange
import com.example.myapplication.data.LightState
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.coroutines.launch

class LightFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_light, container, false)
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.refresh)

        refresh.setOnRefreshListener {
            update()
            refresh.isRefreshing = false
        }
        return view
    }

    var light: Boolean = true
    var minLevel: Int = 0
    var maxLevel: Int = 0
    var nowlevel: Int = 0

    fun update() {
        val main = view!!.findViewById<Button>(R.id.main)
        lifecycleScope.launch {
            val state = WebClient.getLightState()
            light = state.state
            minLevel = state.top
            maxLevel = state.low
            nowlevel = state.value
            main.text = "$nowlevel %"
            on_off.isChecked = light
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        update()
        on_off.setOnClickListener {
            Log.v("CHECK_LISTENER", "begin")
            try {
                lifecycleScope.launch {
                    Log.v("CHECK_LISTENER", "begin2")
                    WebClient.setLightState(
                        LightState(!light, minLevel, maxLevel, 0)
                    )
                    Log.v("CHECK_LISTENER", "begin3")
                    update()
                }
            } catch (e: Exception) {
            }
        }
        main.setOnClickListener {
            (activity as? MainActivity)?.add(RangeFragment(ChangeRange.LIGHT))
        }
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }

}