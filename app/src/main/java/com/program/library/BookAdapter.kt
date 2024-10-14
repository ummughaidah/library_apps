package com.program.library

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.program.library.data.Cart
import com.program.library.data.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookAdapter(
    val results : ArrayList<BookItem>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        showImage(holder, result)
        holder.textViewTitle.text = result.volumeInfo.title
        showAuthor(holder, result)
        showPublisher(holder, result)
        showPublishedDate(holder, result)

        // add cart
        holder.buttonAdd.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    addCart(result) // fungsi untuk menambahkan buku
                    withContext(Dispatchers.Main) {
                        Toast.makeText(holder.itemView.context, "Buku '${result.volumeInfo.title}' berhasil ditambahkan ke keranjang", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(holder.itemView.context, "Gagal menambahkan buku ke keranjang", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getItemCount() = results.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.title_book)
        val textViewAuthor: TextView = view.findViewById(R.id.author)
        val textViewPublisher: TextView = view.findViewById(R.id.publisher)
        val textViewPublishedDate: TextView = view.findViewById(R.id.publishedDate)
        val imageView: ImageView = view.findViewById(R.id.imageUrl)
        val buttonAdd: Button = view.findViewById(R.id.add_cart)
    }

    fun setData(data: List<BookItem>) {
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    // image
    private fun showImage(holder: ViewHolder, result: BookItem) {
        var imageUrl = result.volumeInfo.imageLinks?.smallThumbnail ?: ""
        if (imageUrl.startsWith("http://")) {
            imageUrl = imageUrl.replace("http://", "https://")
        }
        if (imageUrl.isNotEmpty()) {
            Log.d("BookAdapter", imageUrl)
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(holder.imageView)
        } else {
            // Jika tidak ada URL gambar, tampilkan gambar default
            holder.imageView.setImageResource(R.drawable.no_image_available)
        }
    }

    // author
    private fun showAuthor(holder: ViewHolder, result: BookItem) {
        val authors = result.volumeInfo.authors
        if (authors != null && authors.isNotEmpty()) {
            val authorText = if (authors.size > 1) {
                "${authors[0]}, dkk"
            } else {
                authors[0]
            }
            holder.textViewAuthor.text = authorText
        } else {
            holder.textViewAuthor.text = "Penulis tidak diketahui"
        }
    }

    // publisher
    private fun showPublisher(holder: ViewHolder, result: BookItem) {
        val publisher = result.volumeInfo.publisher
        if (publisher != null){
            holder.textViewPublisher.text = publisher
        } else {
            holder.textViewPublisher.text = "Penerbit tidak diketahui"
        }
    }

    // published date
    private fun showPublishedDate(holder: ViewHolder, result: BookItem) {
        val publishedDate = result.volumeInfo.publishedDate
        if (publishedDate != null){
            holder.textViewPublishedDate.text = publishedDate
        } else {
            holder.textViewPublishedDate.text = "N/A"
        }
    }

    // add cart
    private fun addCart(book: BookItem) {
        val bookItem = Cart(
            id = book.id,
            title = book.volumeInfo.title,
            authors = book.volumeInfo.authors?.getOrNull(0) ?: "Penulis tidak diketahui",
            publisher = book.volumeInfo.publisher ?: "Penerbit tidak diketahui",
            publishedDate = book.volumeInfo.publishedDate ?: "N/A",
            description = book.volumeInfo.description ?: "N/A",
            smallThumbnail = book.volumeInfo.imageLinks?.smallThumbnail ?: ""
        )

        // Menyimpan item ke dalam Room menggunakan ViewModel
        cartViewModel.addBook(bookItem)
    }

    // click listener
    interface OnAdapterListener {
        fun onClick(result: BookItem)
    }
}