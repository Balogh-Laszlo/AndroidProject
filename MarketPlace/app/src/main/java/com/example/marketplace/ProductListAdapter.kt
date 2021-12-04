package com.example.marketplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.model.Product

class ProductListAdapter(
    private val context:Context,
    private var list:List<Product>,
    private val listener:OnItemClickListener)
    :RecyclerView.Adapter<ProductListAdapter.ViewHolder>()
{
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvPriceTimeline)
        private val tvSellerName = itemView.findViewById<TextView>(R.id.tvSellerNameTimeline)
        private val tvProductName = itemView.findViewById<TextView>(R.id.tvProductNameTimeline)
        private val ivProductImage = itemView.findViewById<ImageView>(R.id.ivProductImage)
        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }

        fun bind(position: Int) {
            val currentItem = list[position]
            tvPrice.text = "${currentItem.price_per_unit} ${currentItem.price_type}/${currentItem.amount_type}"
            tvSellerName.text = currentItem.username
            tvProductName.text = currentItem.title
            val images = currentItem.images
            if(images != null && images.isNotEmpty()){
                Glide.with(context)
                    .load(images[0].image_path)
                    .override(200)
                    .circleCrop()
                    .into(ivProductImage)
            }
            else{
                Glide.with(context)
                    .load(R.drawable.product)
                    .override(200)
                    .circleCrop()
                    .into(ivProductImage)
            }
        }

        init{
            itemView.setOnClickListener(this)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_product_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Product>){
        list = newList
    }

}

