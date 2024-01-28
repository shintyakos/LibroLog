package com.example.liberolog.repository.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.liberolog.repository.data.entity.BooksEntity

@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<BooksEntity>

    @Insert
    fun insertAll(books: List<BooksEntity>)
}
