package com.example.liberolog.repository.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BooksEntity(
    @PrimaryKey @ColumnInfo(name = "id") val bookId: String,
    val title: String,
    val author: String,
    val coverImageURL: String,
    val publicationDate: String,
    val totalPages: Long,
)
