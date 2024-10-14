package com.program.library.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCart(cart: Cart)

    @Query("SELECT * FROM book_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Cart>>

    @Query("DELETE FROM book_table WHERE id = :id")
    suspend fun deleteCart(id: String)
}