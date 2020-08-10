package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class RangeFragment: Fragment() {
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
    }

}
