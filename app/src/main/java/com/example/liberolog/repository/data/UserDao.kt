package com.example.liberolog.repository.data

import androidx.room.Dao
import androidx.room.Insert
import com.example.liberolog.repository.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)
}
