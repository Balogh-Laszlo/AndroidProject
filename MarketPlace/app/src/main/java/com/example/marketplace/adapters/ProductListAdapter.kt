package com.example.marketplace.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.Product
import com.example.marketplace.model.Screen

class ProductListAdapter(
    private val context:Context,
    private var list:List<Product>,
    private val listener: OnItemClickListener,
    private val screen: Screen,
    private val deleteClickListener: OnDeleteClickListener?,
    private val orderClickListener: OnOrderClickListener?
)
    :RecyclerView.Adapter<ProductListAdapter.ViewHolder>()
{
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
    interface OnDeleteClickListener{
        fun onDeleteClick(position: Int)
    }
    interface OnOrderClickListener{
        fun onOrderClick(position: Int)
    }
    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvPriceTimeline)
        private val tvSellerName = itemView.findViewById<TextView>(R.id.tvSellerNameTimeline)
        private val tvProductName = itemView.findViewById<TextView>(R.id.tvProductNameTimeline)
        private val ivProductImage = itemView.findViewById<ImageView>(R.id.ivProductImage)
        private val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDeleteProduct)
        private val btnOrder = itemView.findViewById<Button>(R.id.btnOrderNow)
        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            if (screen == Screen.MyMarket){
                btnDelete.visibility = View.VISIBLE
                Glide.with(context)
                    .load(R.drawable.ic_delete)
                    .into(btnDelete)
                btnOrder.visibility = View.INVISIBLE
            }
            btnOrder.setOnClickListener {
                orderClickListener?.onOrderClick(position)
            }
            btnDelete.setOnClickListener {
                deleteClickListener?.onDeleteClick(position)
            }
            val currentItem = list[position]
            val price = currentItem.price_per_unit.replace("\"","",true)
            val amountType = currentItem.amount_type.replace("\"","",true)
            val priceType = currentItem.price_type.replace("\"","",true)
            tvPrice.text = "${price} ${priceType}/${amountType}"
            tvSellerName.text = currentItem.username
            val title = currentItem.title.replace("\"","",true)
            tvProductName.text = title
            val images = currentItem.images
            if(images.isNotEmpty()){
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

