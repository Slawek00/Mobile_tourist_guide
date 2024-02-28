package com.example.mobile_tourist_guide.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.data.Routes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CardViewAdapter(private val context: Context): RecyclerView.Adapter<CardViewAdapter.ViewHolder>(){
    private val routes = mutableListOf<Routes>()

    fun submitList(liveDataList: List<Routes>){
        routes.clear()
        routes.addAll(liveDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.main_recyclerview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardViewAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.title.text = routes[position].name
        holder.tag.text = routes[position].tag
        Glide.with(context)
            .load(routes[position].image)
            .into(holder.imageRoute)

        val toPass = Bundle()
        val data = Json.encodeToString(routes[position])
        toPass.putString("DataOfRoute", data)

        holder.card.setOnClickListener { v ->
            val activity = v!!.context as AppCompatActivity

            val navHostFragment =
                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.navigate(
                R.id.action_mainFragment_to_detailFragment,
                toPass
            )
        }

    }

    override fun getItemCount(): Int {
        return routes.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.titleRoute)
        val imageRoute: ImageView = itemView.findViewById(R.id.imageRoute)
        val tag: TextView = itemView.findViewById(R.id.tagRoute)
        val card: CardView = itemView.findViewById(R.id.cardView)
    }

}