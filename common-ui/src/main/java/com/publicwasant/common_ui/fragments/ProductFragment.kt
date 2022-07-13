package com.publicwasant.common_ui.fragments

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.publicwasant.common_ui.R
import com.publicwasant.common_ui.adapters.ProductsRecycleViewAdapter
import com.publicwasant.data_manager.DataManager
import com.publicwasant.data_manager.entities.Product
import com.publicwasant.network_manager.NetworkManager

class ProductFragment : Fragment() {
    private var dataManager: DataManager? = null

    private var swRefresh: SwipeRefreshLayout? = null
    private var rvGeneralProduct: RecyclerView? = null

    private var rvGeneralProductAdapter: ProductsRecycleViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = DataManager(requireContext())

        rvGeneralProductAdapter = ProductsRecycleViewAdapter(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater.inflate(R.layout.fragment_product, container, false)

        swRefresh = views.findViewById(R.id.sw_refresh)
        rvGeneralProduct = views.findViewById(R.id.rv_general_product)

        return views
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        onListenerApply()
        onGeneralProductApply()
        onGeneralProductRefresh()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onListenerApply() {
        swRefresh?.setOnRefreshListener {
            onGeneralProductRefresh()
        }

        NetworkManager.cast(requireContext()) { net ->
            net.onConnect {
                onGeneralProductRefresh()
            }

            net.onDisconnect {}
        }
    }

    private fun onGeneralProductApply() {
        val cardFixedWidth = 340
        val spanCount: Int = Resources.getSystem().displayMetrics.widthPixels/cardFixedWidth

        rvGeneralProduct?.layoutManager = GridLayoutManager(context, spanCount)
        rvGeneralProduct?.adapter = rvGeneralProductAdapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("NotifyDataSetChanged")
    private fun onGeneralProductRefresh() {
        swRefresh?.isRefreshing = true
        dataManager!!.productManager.get { products ->
            if (products!!.isNotEmpty()) {
                rvGeneralProductAdapter?.products = ArrayList(products)
                rvGeneralProductAdapter?.notifyDataSetChanged()
            }

            swRefresh?.isRefreshing = false
        }
    }
}