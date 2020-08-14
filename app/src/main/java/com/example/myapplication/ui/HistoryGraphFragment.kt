package com.example.myapplication.ui

import WebClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.HistoryGraph
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_history_graph.*
import kotlinx.coroutines.launch

class HistoryGraphFragment(val type: HistoryGraph) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_graph, container, false)
        val graphLayout = view.findViewById<GraphView>(R.id.layout)

        fun dataPointMaker(graphY:Array<Int>):Array<DataPoint>{
            val graph = Array(graphY.size) {DataPoint(0.0, 0.0)}
            for (counter in 0 until graphY.size){
                graph[counter] = DataPoint(counter.toDouble(), graphY[counter].toDouble())
            }
            return graph
        }
        when (type) {
            HistoryGraph.HUMIDITY ->
                lifecycleScope.launch {
                    val graphY = Array(72) {0}
                    val state = WebClient.getHistory()
                    for (counter in 0..71) graphY[counter] = state.history[counter].humidityValue
                    graphLayout.addSeries(LineGraphSeries(dataPointMaker(graphY)))
                }

            HistoryGraph.TEMPERATURE_INSIDE ->
                lifecycleScope.launch {
                    val graphY = Array(72) {0}
                    val state = WebClient.getHistory()
                    for (counter in 0..71) graphY[counter] = state.history[counter].temperatureInsideValue
                    graphLayout.addSeries(LineGraphSeries(dataPointMaker(graphY)))
                }
            HistoryGraph.TEMPERATURE_OUTSIDE ->
                lifecycleScope.launch {
                    val graphY = Array(72) {0}
                    val state = WebClient.getHistory()
                    for (counter in 0..71) graphY[counter] = state.history[counter].temperatureOutsideValue
                    graphLayout.addSeries(LineGraphSeries(dataPointMaker(graphY)))
                }
            HistoryGraph.POWER ->
                lifecycleScope.launch {
                    val graphY = Array(72) {0}
                    val state = WebClient.getHistory()
                    for (counter in 0..71) graphY[counter] = state.history[counter].powerValue
                    graphLayout.addSeries(LineGraphSeries(dataPointMaker(graphY)))
                }
            HistoryGraph.PRESSURE ->
                lifecycleScope.launch {
                    val graphY = Array(72) {0}
                    val state = WebClient.getHistory()
                    for (counter in 0..71) graphY[counter] = state.history[counter].pressureValue
                    graphLayout.addSeries(LineGraphSeries(dataPointMaker(graphY)))
                }
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }

}


