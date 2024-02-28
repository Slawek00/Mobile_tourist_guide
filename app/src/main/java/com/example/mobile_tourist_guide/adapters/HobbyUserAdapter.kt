package com.example.mobile_tourist_guide.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_tourist_guide.R

class HobbyUserAdapter: RecyclerView.Adapter<HobbyUserAdapter.ViewHolder>(){
    private val hobby = mutableListOf<String>()

    fun submitList(liveDataList: List<String>){
        hobby.clear()
        hobby.addAll(liveDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyUserAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.userhobby_recyclerview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HobbyUserAdapter.ViewHolder, position: Int) {
        holder.textView.text = hobby[position]
    }

    override fun getItemCount(): Int {
        return hobby.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView  = itemView.findViewById<TextView>(R.id.itemUserHobby)
    }

}