package com.program.library.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Cart(
     @PrimaryKey
    val id: String,
    val title: String,
    val authors: String?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val smallThumbnail: String?,
)
