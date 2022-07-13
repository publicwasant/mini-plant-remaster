package com.publicwasant.common_ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.publicwasant.common_ui.R
import com.publicwasant.data_manager.entities.Promotion

class PromotionsSmallRecycleViewAdapter(
    private val context: Context
): RecyclerView.Adapter<PromotionsSmallRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById<CardView>(R.id.promotion_small_recycler_view_item)
        val tvName: TextView = view.findViewById<TextView>(R.id.tv_name)
        val tvDetails: TextView = view.findViewById<TextView>(R.id.tv_detail)
        val tvDiscountPercent: TextView = view.findViewById<TextView>(R.id.tv_discount_percent)
        val tvTimeDescript: TextView = view.findViewById<TextView>(R.id.tv_time_descript)
    }

    private var clickListenerWork: ((Promotion) -> Unit)? = null

    var data: List<Promotion> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.recycler_view_item_promotion_small, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promotion = data[position]

        holder.tvName.text = promotion.name
        holder.tvDetails.text = promotion.detail
        holder.tvDiscountPercent.text = promotion.discountPercentPlainText()
        holder.tvTimeDescript.text = promotion.start

        holder.parent.setOnClickListener {
            clickListenerWork?.let {
                it(promotion)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setOnClickListener(task: ((Promotion) -> Unit)?) {
        clickListenerWork = task
    }
}