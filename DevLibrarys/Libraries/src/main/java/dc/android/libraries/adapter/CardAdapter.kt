package dc.android.libraries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dc.android.libraries.R

/**
 * ViewPager2 子布局 CardView Adapter
 * @author hf
 */
class CardAdapter(private val cardImgResIds: Array<Int>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val viewHolders = mutableListOf<CardViewHolder>()

    inner class CardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_card, parent, false) as CardView
        return CardViewHolder(cardView).also { viewHolders.add(it) }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardView.findViewById<AppCompatImageView>(R.id.idImg).setImageResource(cardImgResIds[position])
        val idImgShadow = holder.cardView.findViewById<ImageView>(R.id.idImgShadow)
        idImgShadow.setImageResource(cardImgResIds[position])
    }

    override fun getItemCount(): Int {
        return cardImgResIds.size
    }

    fun getViewHolderAtPosition(position: Int) {
        viewHolders.forEach { holder ->
            if (holder.bindingAdapterPosition != position) {
                holder.itemView.findViewById<TextView>(R.id.idBorder)?.visibility = View.GONE
                holder.cardView.findViewById<ImageView>(R.id.idImgShadow)?.visibility = View.GONE
            } else {
                holder.itemView.findViewById<TextView>(R.id.idBorder)?.visibility = View.VISIBLE
                holder.cardView.findViewById<ImageView>(R.id.idImgShadow)?.visibility = View.VISIBLE
            }
        }
    }
}