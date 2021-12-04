package com.example.marketplace

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.model.Image
import java.io.File

class ProductImageAdapter(val context: Context,val images: List<Image>) :
    RecyclerView.Adapter<ProductImageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivImageViewPager)
        fun bind(position: Int) {
//            Glide.with(context)
//                .load(File(images[position].image_path))
//                .into(ivImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.single_image_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = images.size

}
