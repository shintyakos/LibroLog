package com.example.liberolog.repository.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.liberolog.repository.data.entity.BookEntity
import com.example.liberolog.utils.DATABASE_NAME

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookListDao(): BookListDao

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
