package com.publicwasant.common_ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.publicwasant.common_ui.R

class ImagesRecycleViewAdapter(
    private val context: Context
): RecyclerView.Adapter<ImagesRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {
        val parent: LinearLayout = view.findViewById(R.id.image_recycler_view_item)
        val image: ImageView = view.findViewById(R.id.iv_image)
    }

    private var clickListenerWork: ((String) -> Unit)? = null

    var data: List<String> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.recycler_view_item_image, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = data[position]

        Glide.with(context)
            .load(image)
            .centerCrop()
            .into(holder.image)

        holder.parent.setOnClickListener {
            clickListenerWork?.let {
                it(image)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setOnClickListener(task: ((String) -> Unit)?) {
        clickListenerWork = task
    }
}