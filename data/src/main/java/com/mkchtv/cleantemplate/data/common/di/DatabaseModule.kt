package com.mkchtv.cleantemplate.data.common.di

import android.content.Context
import androidx.room.Room
import com.mkchtv.cleantemplate.data.common.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app.db"
        )
            .createFromAsset("db/app.db")
            .build()

    @Provides
    @Singleton
    fun provideElementsDao(db: AppDatabase) = db.elementsDao()
}
