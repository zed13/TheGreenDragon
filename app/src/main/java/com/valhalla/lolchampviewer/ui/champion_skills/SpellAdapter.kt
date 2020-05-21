package com.valhalla.lolchampviewer.ui.champion_skills

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.ui.list.BaseAdapter
import com.valhalla.lolchampviewer.ui.list.ViewHolder

class SpellAdapter : BaseAdapter<SpellData, SpellViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        return SpellViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val item = items[position]

        Picasso.get()
            .load(item.imageUrl)
            .into(holder.spellIconView)

        holder.spellNameView.text = item.title
        holder.spellDescriptionView.text = item.description
    }

}

class SpellViewHolder(parent: ViewGroup) : ViewHolder(parent, R.layout.item_champion_spell) {
    val spellIconView: ImageView = itemView.findViewById(R.id.spell_icon)
    val spellNameView: TextView = itemView.findViewById(R.id.spell_name)
    val spellDescriptionView: TextView = itemView.findViewById(R.id.spell_description)
}