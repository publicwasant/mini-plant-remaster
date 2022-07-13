package com.publicwasant.data_manager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.publicwasant.data_manager.local_db.AppDatabase
import com.publicwasant.data_manager.local_db.dao.IProductDao
import com.publicwasant.data_manager.entities.Product
import com.publicwasant.data_manager.service.Service
import com.publicwasant.data_manager.service.implement.RequestDataImp
import com.publicwasant.data_manager.service.implement.ResponseDataImp
import com.publicwasant.data_manager.service.interfaces.IProductData
import com.publicwasant.network_manager.NetworkManager
import com.publicwasant.network_manager.TransportStatus
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductManager(private val context: Context): RequestDataImp<ResponseDataImp<Product>>() {
    private val localDBDAO: IProductDao = AppDatabase.getInstance(context).product()
    private val serviceRepository: IProductData = Service.retrofit.create(IProductData::class.java)

    /* util */
    private fun merge(productsFromService: List<Product>, productsFromLocal: List<Product>): List<Product> {
        val result: ArrayList<Product> = arrayListOf()

        for (product in productsFromService) {
            val matchedProduct = productsFromLocal.find {
                it.id == product.id
            }

            if (matchedProduct != null) {
                product.isFavorite = matchedProduct.isFavorite
            }

            result.add(product)
        }

        return result
    }

    /* normal */
    @RequiresApi(Build.VERSION_CODES.M)
    fun get(callback: (List<Product>?) -> Unit) {
        if (NetworkManager.isOnline(context)) {
            getFavorites { favoriteProducts ->
                onReceive(serviceRepository.get()).observe(context as LifecycleOwner) { res ->
                    if (res.isNotError()) {
                        Log.i(TAG, res.toString())

                        favoriteProducts?.let {
                            callback.invoke(merge(res.data, it))
                            return@observe
                        }

                        callback.invoke(res.data)
                    } else {
                        Log.e(TAG, res.toString())
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getById(id: Int, callback: (Product?) -> Unit) {
        if (NetworkManager.isOnline(context)) {
            isFavorite(id) { isFavorite ->
                onReceive(serviceRepository.getById(id)).observe(context as LifecycleOwner) { res ->
                    if (res.isNotError()) {
                        res.data[0].isFavorite = isFavorite

                        Log.v(TAG, res.toString())
                        callback.invoke(res.data[0])
                    } else {
                        Log.e(TAG, res.toString())
                    }
                }
            }
        }
    }

    /* favorites manager : CREATE | READ | UPDATE | DELETE product in only local database */
    fun getFavorites(callback: (List<Product>?) -> Unit) {
        localDBDAO.get().observe(context as LifecycleOwner, callback)
    }

    fun isFavorite(product: Product, callback: (Boolean) -> Unit) {
        isFavorite(product.id, callback)
    }

    fun isFavorite(id: Int, callback: (Boolean) -> Unit) {
        localDBDAO.getById(id)
            .observe(context as LifecycleOwner) {
                callback(it != null)
            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addFavorite(product: Product, callback: () -> Unit) {
        GlobalScope.launch {
            localDBDAO.add(product)
            callback.invoke()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun removeFavorite(product: Product, callback: () -> Unit) {
        GlobalScope.launch {
            localDBDAO.delete(product)
            callback.invoke()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun removeFavorite(callback: () -> Unit) {
        GlobalScope.launch {
            localDBDAO.delete()
            callback.invoke()
        }
    }
}