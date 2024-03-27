package com.android.demos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class CardAdapter(private val cards: List<Int>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val viewHolders = mutableListOf<CardViewHolder>()

    inner class CardViewHolder(val cardView: MaterialCardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView: MaterialCardView = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false) as MaterialCardView
        return CardViewHolder(cardView).also { viewHolders.add(it) }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        // 设置卡片内容
        holder.cardView.findViewById<AppCompatImageView>(R.id.idImg).setImageResource(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun getViewHolderAtPosition(position: Int): CardViewHolder? {
        return viewHolders.firstOrNull { it.adapterPosition == position }
    }

    fun findViewHolderAt(position: Int) {

    }
}