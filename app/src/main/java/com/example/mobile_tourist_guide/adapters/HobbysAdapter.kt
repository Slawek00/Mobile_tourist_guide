package com.example.mobile_tourist_guide.adapters


import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.data.Hobbys

class HobbysAdapter: RecyclerView.Adapter<HobbysAdapter.ViewHolder>() {

    private val hobbysList = mutableListOf<Hobbys>()
    private val selectedList = mutableListOf<Hobbys>()
    private val checkBoxStatesArray = SparseBooleanArray()


    fun submitList(liveDataList: List<Hobbys>){
        hobbysList.clear()
        selectedList.clear()
        hobbysList.addAll(liveDataList)
        notifyDataSetChanged()
    }


    fun returnList():List<Hobbys>{
        return selectedList
    }


    fun clearList(){
        selectedList.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.questionary_recycleview_item, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return hobbysList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.text = hobbysList[position].name.toString()
        holder.checkBox.isChecked = checkBoxStatesArray.get(position, false)
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                checkBoxStatesArray.put(holder.adapterPosition, true)
            }else if(!isChecked){
                checkBoxStatesArray.put(holder.adapterPosition, false)
            }
        }
        holder.checkBox.setOnClickListener {
            if(holder.checkBox.isChecked && !selectedList.contains(hobbysList[position])){
                selectedList.add(hobbysList.get(position))
            }else{
                selectedList.remove(hobbysList.get(position))
            }
        }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val checkBox = itemView.findViewById<CheckBox>(R.id.item)
    }


}