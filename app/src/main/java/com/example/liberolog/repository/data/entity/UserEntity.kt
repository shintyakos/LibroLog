package com.example.liberolog.repository.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val userId: String,
    @ColumnInfo(name = "name")
    val userName: String,
    @ColumnInfo(name = "email")
    val email: String,
)
