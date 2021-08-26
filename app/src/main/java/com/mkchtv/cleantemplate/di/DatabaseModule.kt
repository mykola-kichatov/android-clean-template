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

    @Volatile
    private var INSTANCE: ElementsDatabase? = null

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ElementsDatabase {
        return INSTANCE ?: synchronized(this) {
            val databaseInstance = Room.databaseBuilder(
                context,
                ElementsDatabase::class.java, "elements.db"
            ).build()
            INSTANCE = databaseInstance
            return databaseInstance
        }
    }

    @Provides
    @Singleton
    fun provideElementsDao(db: ElementsDatabase) = db.elementsDao()

}