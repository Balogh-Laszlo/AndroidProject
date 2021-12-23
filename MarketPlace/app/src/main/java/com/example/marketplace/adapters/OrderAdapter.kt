package com.example.marketplace.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.persistableBundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.Order
import com.example.marketplace.model.OrderScreen
import java.sql.Timestamp
import java.util.*

class OrderAdapter(val context: Context, var orders:List<Order>,val  screen: OrderScreen, val listener: OnItemSelected?): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    interface OnItemSelected{
        fun onItemSelected(position: Int, status:String)
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        AdapterView.OnItemSelectedListener {
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUserNameOrders)
        val ivUserPhoto = itemView.findViewById<ImageView>(R.id.ivUserPhotoOrders)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDateOrders)
        val ivProductPhoto = itemView.findViewById<ImageView>(R.id.ivProductOrders)
        val spStatus = itemView.findViewById<Spinner>(R.id.spStatusOrders)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitleOrders)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescriptionOrders)
        val tvAmount = itemView.findViewById<TextView>(R.id.tvAmountOrders)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPriceOrders)
        val btnDecline = itemView.findViewById<ImageButton>(R.id.btnDeclineOrders)
        val btnAccept = itemView.findViewById<ImageButton>(R.id.btnAcceptOrders)
        val btnShowMore = itemView.findViewById<ImageButton>(R.id.btnShowMore)
        val tvStatus = itemView.findViewById<TextView>(R.id.tvStatusOrders)
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            var id =0
            if(orders[position].status == "DECLINED"){
                btnDecline.visibility = View.INVISIBLE
                Glide.with(context)
                    .load(R.drawable.ic_close_white)
                    .into(btnAccept)
            }else if(orders[position].status == "OPEN" && screen == OrderScreen.OngoingOrders){
                btnDecline.visibility = View.INVISIBLE
                Glide.with(context)
                    .load(R.drawable.ic_question)
                    .into(btnAccept)
            }else if(orders[position].status == "OPEN"){

            }
            else{
                btnDecline.visibility = View.INVISIBLE
            }

            btnShowMore.setOnClickListener {
                if(tvDescription.isVisible){
                    Glide.with(context)
                        .load(R.drawable.ic_down)
                        .into(btnShowMore)
                    tvDescription.visibility = View.INVISIBLE
                    spStatus.visibility = View.INVISIBLE
                    tvStatus.visibility = View.VISIBLE
                }else{
                    Glide.with(context)
                        .load(R.drawable.ic_up)
                        .into(btnShowMore)
                    tvDescription.visibility = View.VISIBLE
                    if(screen == OrderScreen.OngoingSales) {
                        spStatus.visibility = View.VISIBLE
                        tvStatus.visibility = View.INVISIBLE
                    }

                    id =0
                    id = when(orders[position].status){
                        "OPEN" -> 0
                        "ACCEPTED" -> 1
                        "DECLINED" -> 2
                        "DELIVERING" -> 3
                        else -> 4
                    }
                    spStatus.setSelection(id)
                }
            }

            tvDescription.text = orders[position].description.replace("\"","")
            if(screen == OrderScreen.OngoingSales){
                tvUserName.text = orders[position].username.replace("\"","")
            }
            else{
                tvUserName.text = orders[position].owner_username.replace("\"","")
            }
            tvDate.text = Date(Timestamp(orders[position].creation_time).time).toLocaleString()
            if(orders[position].images.isNotEmpty()){
                Glide.with(context)
                    .load(orders[position].images[0])
                    .circleCrop()
                    .into(ivProductPhoto)
            }else{
                Glide.with(context)
                    .load(R.drawable.product)
                    .circleCrop()
                    .into(ivProductPhoto)
            }
            tvTitle.text = orders[position].title.replace("\"","").replace("\\","")
            tvAmount.text = orders[position].units.replace("\"","").replace("\\","")+ "L"
            tvPrice.text = orders[position].price_per_unit.replace("\"","").replace("\\","") + " Ron"
            val statusTypes = context.resources.getStringArray(R.array.StatusType)
            var adapter = ArrayAdapter(context,R.layout.spinner_list_blue,statusTypes)
            adapter.setDropDownViewResource(R.layout.spinner_list_blue)
            spStatus.adapter = adapter
            id =0
            id = when(orders[position].status){
                "OPEN" -> 0
                "ACCEPTED" -> 1
                "DECLINED" -> 2
                "DELIVERING" -> 3
                else -> 4
            }
            tvStatus.text = statusTypes[id]
            spStatus.onItemSelectedListener = this
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            Log.d("xxx","OnItemSelected")
            var status = when(p2){
                0 -> "OPEN"
                1 -> "ACCEPTED"
                2 -> "DECLINED"
                3 -> "DELIVERING"
                else -> "DELIVERED"
            }
            val statusTypes = context.resources.getStringArray(R.array.StatusType)

//            orders[position].status = status
            Log.d("xxx","Status"+ status +"OnItemSelected"+orders[position].toString())
            tvStatus.text = statusTypes[p2]
            listener?.onItemSelected(position, status)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.singel_order_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = orders.size
    fun setList(list: List<Order>){
        orders = list
    }
}