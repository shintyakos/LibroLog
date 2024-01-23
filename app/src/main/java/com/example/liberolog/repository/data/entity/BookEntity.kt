package com.example.liberolog.repository.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booklist")
data class BookEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val image: String,
)
