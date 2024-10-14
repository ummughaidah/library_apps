package com.program.library.data

import androidx.lifecycle.LiveData

class CartRepository(private val cartDao: CartDao) {

     val readAllData: LiveData<List<Cart>> = cartDao.readAllData()

    suspend fun addCart(cart: Cart) {
         cartDao.addCart(cart)
    }

    suspend fun deleteCart(id: String) {
        cartDao.deleteCart(id)
    }
}