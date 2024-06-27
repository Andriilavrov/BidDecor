package com.example.biddecor.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.biddecor.LotActivity
import com.example.biddecor.R
import com.squareup.picasso.Picasso

class LotsAdapter(val lots: List<Lot>, var context: Context) :
    RecyclerView.Adapter<LotsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.lotListImage)
        val title: TextView = view.findViewById(R.id.lotListTitle)
        val price: TextView = view.findViewById(R.id.lotListPrice)
        val deadline: TextView = view.findViewById(R.id.lotListDeadline)
        val button: Button = view.findViewById(R.id.lotListButton)

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
        Picasso.get().load(lots[position].ImageDataRef).into(holder.image)
        holder.button.setOnClickListener {
            val intent = Intent(context, LotActivity::class.java)
            intent.putExtra("lotTitle", lots[position].title)
            intent.putExtra("lotStartPrice", lots[position].startPrice)
            intent.putExtra("lotCategory", lots[position].category)
            intent.putExtra("lotDeadline", lots[position].deadline)
            intent.putExtra("lotDesc", lots[position].description)
            context.startActivity(intent)
        }
    }
}