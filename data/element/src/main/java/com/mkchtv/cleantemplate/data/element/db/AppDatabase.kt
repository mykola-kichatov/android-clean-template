package com.mkchtv.cleantemplate.data.element.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkchtv.cleantemplate.data.element.dao.ElementsDao
import com.mkchtv.cleantemplate.data.element.entity.ElementEntity

@Database(entities = [ElementEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun elementsDao(): ElementsDao
}
