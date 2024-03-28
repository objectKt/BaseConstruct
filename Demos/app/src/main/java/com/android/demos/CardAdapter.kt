package com.android.demos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class CardAdapter(private val cards: Array<Int>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val viewHolders = mutableListOf<CardViewHolder>()

    inner class CardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false) as CardView
        return CardViewHolder(cardView).also { viewHolders.add(it) }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        // 设置卡片内容
        holder.cardView.findViewById<AppCompatImageView>(R.id.idImg).setImageResource(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun getViewHolderAtPosition(position: Int) {
        viewHolders.forEach { holder ->
            if (holder.adapterPosition != position) {
                holder.itemView.findViewById<TextView>(R.id.idBorder)?.visibility = View.GONE
            } else {
                holder.itemView.findViewById<TextView>(R.id.idBorder)?.visibility = View.VISIBLE
            }
        }
    }
}