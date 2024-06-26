package com.example.biddecor.model

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.biddecor.R

class LotsAdapter(val lots: List<Lot>, var context: Context) :
    RecyclerView.Adapter<LotsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.lotImage)
        val title: TextView = view.findViewById(R.id.lotTitle)
        val price: TextView = view.findViewById(R.id.lotPrice)
        val deadline: TextView = view.findViewById(R.id.lotDeadline)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lot_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lots.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = lots[position].title
        holder.price.text = lots[position].startPrice.toString()
        holder.deadline.text = lots[position].deadline

    }
}