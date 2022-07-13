package com.publicwasant.common_ui.activities

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.publicwasant.common_ui.R
import com.publicwasant.common_ui.adapters.ImagesRecycleViewAdapter
import com.publicwasant.common_ui.adapters.PromotionsSmallRecycleViewAdapter
import com.publicwasant.data_manager.DataManager
import com.publicwasant.data_manager.entities.Product
import com.publicwasant.network_manager.NetworkManager

class ProductDetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private val dataManager: DataManager = DataManager(this)

    private var isFavorite: Boolean = false
    private var isFavoriteToggleEnable: Boolean = true

    private var product: Product? = null

    /* contents */
    private var swRefresh: SwipeRefreshLayout? = null
    private var llPromotion: LinearLayout? = null
    private var tvActualPrice: TextView? = null
    private var tvOriginalPrice: TextView? = null
    private var tvDiscountPercent: TextView? = null
    private var tvName: TextView? = null
    private var tvDetail: TextView? = null
    private var tvType: TextView? = null
    private var tvSize: TextView? = null
    private var ivFavorite: ImageView? = null
    private var rvImages: RecyclerView? = null
    private var rvPromotion: RecyclerView? = null
    private var rvImagesAdapter: ImagesRecycleViewAdapter? = null
    private var rvPromotionAdapter: PromotionsSmallRecycleViewAdapter? = null

    /* navigator */
    private var ivBack: ImageView? = null
    private var ivOption: ImageView? = null

    /* transaction */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra("PRODUCT") as Product
        if (product == null) {
            finish()
        }

        isFavorite = intent.getBooleanExtra("IS_FAVORITE", false)

        /* init content views */
        swRefresh = findViewById(R.id.sw_refresh)
        llPromotion = findViewById(R.id.ll_promotion)
        tvActualPrice = findViewById(R.id.tv_actual_price)
        tvOriginalPrice = findViewById(R.id.tv_original_price)
        tvDiscountPercent = findViewById(R.id.tv_discount_percent)
        tvName = findViewById(R.id.tv_name)
        tvDetail = findViewById(R.id.tv_detail)
        tvType = findViewById(R.id.tv_type)
        tvSize = findViewById(R.id.tv_size)
        ivFavorite = findViewById(R.id.iv_favorite)
        rvImages = findViewById(R.id.rv_images)
        rvPromotion = findViewById(R.id.rv_promotion)

        /* init navigator views */
        ivBack = findViewById(R.id.iv_back)
        ivOption = findViewById(R.id.iv_option)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        onListenerApply()
        onProductApply()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRefresh() {
        swRefresh?.isRefreshing = true
        dataManager.productManager.getById(product!!.id) { updatedProduct ->
            if (updatedProduct != null) {
                product = updatedProduct
                product?.isFavorite = isFavorite
                onProductApply()
            }

            swRefresh?.isRefreshing = false
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_favorite -> onFavoriteToggle()
            R.id.iv_back -> onBackPressed()
            R.id.iv_option -> finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onListenerApply() {
        swRefresh?.setOnRefreshListener(this)
        ivFavorite?.setOnClickListener(this)
        ivBack?.setOnClickListener(this)
        ivOption?.setOnClickListener(this)

        NetworkManager.cast(this) { net ->
            net.onConnect {
                onRefresh()
            }

            net.onDisconnect {}
        }
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun onProductApply() {
        /* images */
        val imageLayoutManager = LinearLayoutManager(this)
        imageLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvImagesAdapter = ImagesRecycleViewAdapter(this)
        rvImages?.layoutManager = imageLayoutManager
        rvImages?.adapter = rvImagesAdapter
        rvImagesAdapter?.data = product!!.images
        rvImagesAdapter?.notifyDataSetChanged()

        /* prices */
        tvActualPrice?.text = product?.price?.actualPlainText()
        if (product?.price?.discount?.isDiscount() == true) {
            tvOriginalPrice?.text = product?.price?.originalPlainText()
            tvOriginalPrice?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG.inc()
            tvDiscountPercent?.text = product?.price?.discount?.percentPlainText()

            val promotionLayoutManager = LinearLayoutManager(this)
            promotionLayoutManager.orientation = LinearLayoutManager.VERTICAL

            rvPromotionAdapter = PromotionsSmallRecycleViewAdapter(this)
            rvPromotion?.layoutManager = promotionLayoutManager
            rvPromotion?.adapter = rvPromotionAdapter
            rvPromotionAdapter?.data = product?.promotions!!
            rvPromotionAdapter?.notifyDataSetChanged()
        } else {
            tvOriginalPrice?.text = "ยังไม่มีส่วนลด"
            llPromotion?.visibility = View.GONE
        }

        /* favorite */
        if (isFavorite) {
            ivFavorite?.setImageResource(R.drawable.ic_favorite)
        } else {
            ivFavorite?.setImageResource(R.drawable.ic_favorite_border)
        }

        /* details */
        tvName?.text = product?.name
        tvDetail?.text = product?.detail
        tvType?.text = "ประเภท: ${product?.typePlainText()}"
        tvSize?.text = "ขนาด: ${product?.sizePlainText()}"
    }

    private fun onFavoriteToggle() {
        if (!isFavoriteToggleEnable) {
            return
        }

        isFavoriteToggleEnable = false

        if (isFavorite) {
            ivFavorite?.setImageResource(R.drawable.ic_favorite_border)
            dataManager.productManager.removeFavorite(product!!) {
                isFavoriteToggleEnable = true
            }
        } else {
            ivFavorite?.setImageResource(R.drawable.ic_favorite)
            dataManager.productManager.addFavorite(product!!) {
                isFavoriteToggleEnable = true
            }
        }

        isFavorite = !isFavorite
    }
}