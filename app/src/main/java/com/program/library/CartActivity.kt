package com.program.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.program.library.data.CartViewModel

class CartActivity : AppCompatActivity() {

    private lateinit var cartViewModel: CartViewModel

    lateinit var cartAdapter: CartAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // title appBar
        supportActionBar?.title = "Cart"

        recyclerView = findViewById(R.id.recyclerView_cart)
        setupRecycleView()
        showDataCart()
    }

    private fun setupRecycleView() {
        cartAdapter = CartAdapter(arrayListOf()) { bookId ->
            cartViewModel.deleteBook(bookId)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun showDataCart() {
        showLoading(true)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        // LiveData dari CartViewModel
        cartViewModel.readAllData.observe(this) { cartList ->
            cartAdapter.setData(cartList)
            showLoading(false)
        }
    }

    // loading
    private fun showLoading(loading: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar_cart)
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }
}