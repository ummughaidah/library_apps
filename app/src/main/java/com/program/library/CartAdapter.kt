package com.program.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.program.library.data.Cart

class CartAdapter(
    val results: ArrayList<Cart>,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart, parent, false)
        return CartAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val result = results[position]
        // image
        Glide.with(holder.itemView.context)
            .load(result.smallThumbnail)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.no_image_available)
            .centerCrop()
            .into(holder.imageView)

        // text view
        holder.textViewTitle.text = result.title
        holder.textViewAuthor.text = result.authors
        holder.textViewPublisher.text = result.publisher
        holder.textViewPublishedDate.text = result.publishedDate

        // delete cart
        holder.deleteButton.setOnClickListener {
            onDeleteClick(result.id)
        }
    }

    override fun getItemCount() = results.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.title_book_cart)
        val textViewAuthor: TextView = view.findViewById(R.id.author_cart)
        val textViewPublisher: TextView = view.findViewById(R.id.publisher_cart)
        val textViewPublishedDate: TextView = view.findViewById(R.id.publishedDate_cart)
        val imageView: ImageView = view.findViewById(R.id.imageUrlCart)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button_cart)
    }

    fun setData(data: List<Cart>) {
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }
}