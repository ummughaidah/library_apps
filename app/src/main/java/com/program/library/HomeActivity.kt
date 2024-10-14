package com.program.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.program.library.data.CartViewModel
import com.program.library.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeActivity : AppCompatActivity() {

    private val TAG: String = "HomeActivity"
    lateinit var bookAdapter: BookAdapter
    private val cartViewModel: CartViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var bookList: ArrayList<BookItem>  // Daftar utama buku dari API
    private lateinit var searchList: ArrayList<BookItem> // hasil search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inisialisasi recyclerView
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)

        // search
        bookList = arrayListOf()
        searchList = arrayListOf()

        setupRecycleView()
        searchData()
        setupFAB()

        // keylogger
        // Mengakses EditText di dalam SearchView menggunakan ID internal dari Android
        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        // Menetapkan AccessibilityDelegate ke EditText
        val delegate = MyA11yDelegate()
        searchEditText.accessibilityDelegate = delegate
    }

    override fun onStart() {
        super.onStart()
        // Ambil teks dari SearchView
        val query = searchView.query.toString()

        // menentukan pencarian
        if (query.isNotEmpty()) {
            getBooksFromApi(query)
        } else {
            getBooksFromApi("technology")
        }
    }

    private fun setupRecycleView() {
        bookAdapter = BookAdapter(arrayListOf(), cartViewModel)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
        }
    }

    private fun showData(data: HomeModel) {
        val bookItems = data.items
        // Simpan data API ke bookList
        bookList.clear()
        bookList.addAll(bookItems)

        // Tampilkan data awal di recyclerView
        bookAdapter.setData(bookList)
    }

    // searchData
    private fun searchData() {
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Saat submit, panggil API dengan query yang dimasukkan
                query?.let {
                    getBooksFromApi(it)  // Memanggil API dengan query dari searchView
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // Opsi: panggil API saat teks berubah, atau bisa tetap menunggu submit
                return false
            }
        })
    }

    // getBooksFromApi agar menerima query
    private fun getBooksFromApi(query: String) {
        showLoading(true)
        ApiService.endpoint.getBooks(query)  // query dari searchView
            .enqueue(object : Callback<HomeModel> {

                override fun onResponse(call: Call<HomeModel>, response: Response<HomeModel>) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null && result.items != null) {
                            showData(result)
                            printLog("Data received successfully: ${result.items}")
                        } else {
                            printLog("Received empty response body")
                        }
                        val booksData = result?.toString() ?: "No items available"
                        printLog(booksData)
                    } else {
                        printLog("HomeActivity ERROR!")
                    }
                }

                override fun onFailure(call: Call<HomeModel>, t: Throwable) {
                    printLog(t.toString())
                    showLoading(false)
                }
            })
    }


    // log
    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    // loading
    private fun showLoading(loading: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    // floating action button
    private fun setupFAB() {
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            // throw RuntimeException("Test Crash")
            // go to page home
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}