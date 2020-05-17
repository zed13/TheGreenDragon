package com.valhalla.lolchampviewer.ui.champion_details_fragment

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.ui.list.BaseAdapter

class SkinsCarouselAdapter : BaseAdapter<String, SkinsCarouselViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkinsCarouselViewHolder {
        return SkinsCarouselViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SkinsCarouselViewHolder, position: Int) {
        val item = items[position]

        Picasso.get()
            .load(item)
            .into(holder.imageView)
    }

}

class SkinsCarouselViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(createImageView(parent)) {

    val imageView: ImageView = itemView as ImageView

    companion object {
        private fun createImageView(parent: ViewGroup): ImageView {
            return ImageView(parent.context).apply {
                adjustViewBounds = true
            }
        }
    }
}