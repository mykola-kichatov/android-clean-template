package com.mkchtv.cleantemplate.data.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkchtv.cleantemplate.data.element.dao.ElementsDao
import com.mkchtv.cleantemplate.data.element.entity.ElementEntity

@Database(entities = [ElementEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun elementsDao(): ElementsDao
}
