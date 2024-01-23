package com.example.liberolog.repository.data

import androidx.room.Dao
import androidx.room.Query
import com.example.liberolog.repository.data.entity.BookEntity

@Dao
interface BookListDao {
    @Query("SELECT * FROM booklist")
    fun getAll(): List<BookEntity>
}
