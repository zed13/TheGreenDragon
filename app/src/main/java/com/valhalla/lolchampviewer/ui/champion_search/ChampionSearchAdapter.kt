package com.valhalla.lolchampviewer.ui.champion_search

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.ui.list.BaseAdapter
import com.valhalla.lolchampviewer.ui.list.ViewHolder

class ChampionSearchAdapter(
    val onClickListener: ((ChampionSearchViewData) -> Unit)? = null
) : BaseAdapter<ChampionSearchViewData, ChampionViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val textColorSpan = ForegroundColorSpan(Color.GREEN)

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
        val (item, query) = items[position]
        Picasso.get()
            .load(DataDragonApi.getIconAddress(item.image.full))
            .into(holder.imageView)
        holder.champNameView.text = highlightQuery(item.name, query)
        holder.champTitleView.text = highlightQuery(item.title, query)
    }

    private fun highlightQuery(text: String, query: String): CharSequence {
        if (query.isEmpty()) return text
        val index = text.indexOf(query, ignoreCase = true)
        if (index == -1) return text
        val spannable = SpannableString(text)
        spannable.setSpan(
            textColorSpan,
            index,
            index + query.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    override fun getItemId(position: Int): Long {
        return items[position].champion.intKey
    }

}

class ChampionViewHolder(parent: ViewGroup) : ViewHolder(parent, R.layout.item_champion_list) {
    val imageView: ImageView = itemView.findViewById(R.id.champ_icon)
    val champNameView: TextView = itemView.findViewById(R.id.champ_name)
    val champTitleView: TextView = itemView.findViewById(R.id.champ_title)
}