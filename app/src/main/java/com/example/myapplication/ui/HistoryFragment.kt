package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class HistoryFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val graph = view.findViewById<GraphView>(R.id.layout)
        val graphSeries  = LineGraphSeries(arrayOf(
            DataPoint(2.0, 4.0),
            DataPoint(3.0, 12.0),
            DataPoint(4.0, 16.0),
            DataPoint(5.0, 18.0),
            DataPoint(6.0, 19.0)
        ))
        graph.addSeries(graphSeries)

        return view
    }

}
