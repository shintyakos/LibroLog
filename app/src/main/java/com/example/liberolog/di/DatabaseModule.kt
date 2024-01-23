package com.example.liberolog.di

import com.example.liberolog.repository.data.AppDatabase
import com.example.liberolog.repository.data.BookListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideBookListDao(appDatabase: AppDatabase): BookListDao {
        return appDatabase.bookListDao()
    }
}
