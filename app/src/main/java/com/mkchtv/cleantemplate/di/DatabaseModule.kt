package com.mkchtv.cleantemplate.di

import android.content.Context
import androidx.room.Room
import com.mkchtv.cleantemplate.data.db.ElementsDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): ElementsDatabase =
        Room.databaseBuilder(
            context,
            ElementsDatabase::class.java, "elements.db"
        )
            .createFromAsset("db/elements.db")
            .build()

    @Provides
    @Singleton
    fun provideElementsDao(db: ElementsDatabase) = db.elementsDao()

}