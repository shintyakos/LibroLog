package com.example.liberolog.repository.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.liberolog.repository.data.entity.BooksEntity
import com.example.liberolog.repository.data.entity.UserEntity
import com.example.liberolog.utils.DATABASE_NAME

@Database(entities = [BooksEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun booksDao(): BooksDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context = context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return androidx.room.Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME,
            ).build()
        }
    }
}
