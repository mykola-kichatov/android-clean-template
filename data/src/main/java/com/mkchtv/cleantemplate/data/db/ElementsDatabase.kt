package com.mkchtv.cleantemplate.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkchtv.cleantemplate.data.entity.ElementEntity

@Database(entities = [ElementEntity::class], version = 1)
abstract class ElementsDatabase : RoomDatabase() {

    abstract fun elementsDao(): ElementsDao

}