package com.example.mobile_tourist_guide.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.data.FavoriteRoutes

class FavoriteAdapter(private val context: Context): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val routes = mutableListOf<FavoriteRoutes>()

    fun submitList(routesLiveData: List<FavoriteRoutes>){
        routes.clear()
        routes.addAll(routesLiveData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.favorite_recyclerview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(routes[position].image_src)
            .circleCrop()
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return routes.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.favImageRoute)
    }
}