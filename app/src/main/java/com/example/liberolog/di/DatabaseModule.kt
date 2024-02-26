package com.example.liberolog.di

import android.content.Context
import com.example.liberolog.repository.data.AppDatabase
import com.example.liberolog.repository.data.BooksDao
import com.example.liberolog.repository.data.UserDao
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
    fun provideBookListDao(appDatabase: AppDatabase): BooksDao {
        return appDatabase.booksDao()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}
