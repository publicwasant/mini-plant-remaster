package com.publicwasant.common_ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.publicwasant.common_ui.R
import com.publicwasant.common_ui.activities.ProductDetailActivity
import com.publicwasant.data_manager.DataManager
import com.publicwasant.data_manager.entities.Product
import java.io.Serializable

class ProductsRecycleViewAdapter(
    private val context: Context
): RecyclerView.Adapter<ProductsRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {
        /* init views */
        val parent: CardView = view.findViewById(R.id.product_recycler_view_item)
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvDetails: TextView = view.findViewById(R.id.tv_detail)
        val tvType: TextView = view.findViewById(R.id.tv_type)
        val tvSize: TextView = view.findViewById(R.id.tv_size)
        val tvActualPrice: TextView = view.findViewById(R.id.tv_actual_price)
        val tvOriginalPrice: TextView = view.findViewById(R.id.tv_original_price)
        val tvDiscountPercent: TextView = view.findViewById(R.id.tv_discount_percent)
        val ivFavorite: ImageView = view.findViewById(R.id.iv_favorite)
        val ivClose: ImageView = view.findViewById(R.id.iv_close)
        val ivImage: ImageView = view.findViewById(R.id.iv_image)

        /* init variables */
        var isFavorite: Boolean = false
        var isFavoriteToggleEnable: Boolean = true
    }

    private val dataManager: DataManager = DataManager(context)

    var products: ArrayList<Product> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.recycler_view_item_product, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        onProductApply(holder, product)
        onListenerApply(holder, product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private fun onListenerApply(holder: ViewHolder, product: Product) {
        holder.ivClose.bringToFront()
        holder.ivClose.setOnClickListener {
            val closeAt = products.indexOf(product)

            products.removeAt(closeAt)
            notifyItemRemoved(closeAt)
        }

        holder.ivFavorite.bringToFront()
        holder.ivFavorite.setOnClickListener {
            onFavoriteToggle(holder, product)
        }

        holder.parent.setOnClickListener {
            val openProductDetail = Intent(context, ProductDetailActivity::class.java)
            openProductDetail.putExtra("PRODUCT", product as Serializable)
            openProductDetail.putExtra("IS_FAVORITE", holder.isFavorite)

            context.startActivity(openProductDetail)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onProductApply(holder: ViewHolder, product: Product) {
        /* image */
        Glide.with(context)
            .load(product.images[0])
            .centerCrop()
            .into(holder.ivImage)

        /* detail */
        holder.tvName.text = product.name
        holder.tvDetails.text = product.detail
        holder.tvType.text = "ประเภท: ${product.typePlainText()}"
        holder.tvSize.text = "ขนาด: ${product.sizePlainText()}"

        /* prices */
        holder.tvActualPrice.text = product.price.actualPlainText()
        if (product.price.discount.isDiscount()) {
            holder.tvOriginalPrice.text = product.price.originalPlainText()
            holder.tvOriginalPrice.paintFlags = holder.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG.inc()
            holder.tvDiscountPercent.text = product.price.discount.percentPlainText()
        } else {
            holder.tvOriginalPrice.text = "ราคาเริ่มต้น"
        }

        /* favorite */
        holder.isFavorite = product.isFavorite
        if (holder.isFavorite) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun onFavoriteToggle(holder: ViewHolder, product: Product) {
        if (!holder.isFavoriteToggleEnable) {
            return
        }

        holder.isFavoriteToggleEnable = false

        if (holder.isFavorite) {
            product.isFavorite = false
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
            dataManager.productManager.removeFavorite(product) {
                holder.isFavoriteToggleEnable = true
            }
        } else {
            product.isFavorite = true
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite)
            dataManager.productManager.addFavorite(product) {
                holder.isFavoriteToggleEnable = true
            }
        }

        holder.isFavorite = !holder.isFavorite
    }
}