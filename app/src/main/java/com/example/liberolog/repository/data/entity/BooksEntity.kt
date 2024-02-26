package com.example.liberolog.repository.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BooksEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val bookId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "url")
    val coverImageURL: String,
    @ColumnInfo(name = "publish_date")
    val publicationDate: String,
    @ColumnInfo(name = "pages")
    val totalPages: Long,
)
