package com.example.myapplication.ui

import WebClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.LockHistoryItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_lock_history.*
import kotlinx.android.synthetic.main.item_lock_history.view.*
import kotlinx.coroutines.launch

class LockHistoryFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lock_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = Adapter()
        lock_history.layoutManager = LinearLayoutManager (view.context)
        lock_history.adapter = adapter
        lifecycleScope.launch{
            val history = WebClient.getLockHistory()
            adapter.setNewList(history.history.toList())
        }
        back.setOnClickListener {
            (activity as? MainActivity)?.back()
        }
    }
    class Adapter(): RecyclerView.Adapter<Adapter.ViewHolder>() {
        fun setNewList(newList: List<LockHistoryItem>) {
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }

        val list = mutableListOf<LockHistoryItem>()
        class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),LayoutContainer{
            fun set (element: LockHistoryItem){
                containerView.type.text = element.type
                containerView.time.text = element.time
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_lock_history, parent, false)
            return ViewHolder(view)

        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.set(list[position])
        }
    }
}