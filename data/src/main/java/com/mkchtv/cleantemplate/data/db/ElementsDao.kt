package com.mkchtv.cleantemplate.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mkchtv.cleantemplate.data.entity.ElementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementsDao {

    @Query("SELECT * FROM elements ORDER BY id ASC")
    fun elementsFlow(): Flow<List<ElementEntity>>

}