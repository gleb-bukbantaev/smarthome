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
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.refresh_light)

        refresh.setOnRefreshListener {
            update()
            refresh.isRefreshing = false
        }
        return view
}

    var lamp: Boolean = false
    var minLevel: Int = 0
    var maxLevel: Int = 0
    var nowlevel: Int = 0

    fun update() {
        val lightValue = view!!.findViewById<Button>(R.id.light_main)
        lifecycleScope.launch {
            val state = WebClient.getLightState()
            lamp = state.state
            minLevel = state.top
            maxLevel = state.low
            nowlevel = state.value
            lightValue.text = "$nowlevel %"
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        update()
        lightOnOff.setOnClickListener {
          try {
              lifecycleScope.launch {
                  WebClient.setLightState(
                      LightState(!lamp, minLevel, maxLevel, 0)
                  )
                  update()
              }
          }catch (e:Exception){}
        }
    }

}