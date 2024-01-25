package com.example.liberolog.di

import android.content.Context
import com.example.liberolog.repository.data.AppDatabase
import com.example.liberolog.repository.data.BookListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideBookListDao(appDatabase: AppDatabase): BookListDao {
        return appDatabase.bookListDao()
    }
}
