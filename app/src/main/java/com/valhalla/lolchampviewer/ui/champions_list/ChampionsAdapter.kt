package com.valhalla.lolchampviewer.ui.champions_list

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.ui.list.BaseAdapter
import com.valhalla.lolchampviewer.ui.list.ViewHolder

class ChampionsAdapter(val onClickListener: ((ChampionShort) -> Unit)? = null) :
    BaseAdapter<ChampionShort, ChampionViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        return ChampionViewHolder(parent).apply {
            itemView.setOnClickListener {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClickListener?.invoke(items[adapterPosition])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        val item = items[position]
        Picasso.get()
            .load(DataDragonApi.getIconAddress(item.image.full))
            .into(holder.imageView)
        holder.champNameView.text = item.name
        holder.champTitleView.text = item.title
    }

    override fun getItemId(position: Int): Long {
        return items[position].intKey
    }

}

class ChampionViewHolder(parent: ViewGroup) : ViewHolder(parent, R.layout.item_champion_list) {
    val imageView: ImageView = itemView.findViewById(R.id.champ_icon)
    val champNameView: TextView = itemView.findViewById(R.id.champ_name)
    val champTitleView: TextView = itemView.findViewById(R.id.champ_title)
}