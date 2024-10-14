package com.program.library.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Cart>>
    private val repository: CartRepository

    init {
        val cartDao = CartDatabase.getDatabase(application).cartDao()
        repository = CartRepository(cartDao)
        readAllData = repository.readAllData
    }

    fun addBook(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCart(cart)
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCart(id)
        }
    }
}