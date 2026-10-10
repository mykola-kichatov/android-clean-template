package com.mkchtv.cleantemplate.data.element.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mkchtv.cleantemplate.data.element.entity.ElementEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ElementsDao {

    @Query("SELECT * FROM elements ORDER BY id")
    fun elementsFlow(): Flow<List<ElementEntity>>

    @Query("SELECT * FROM elements WHERE id = :elementId LIMIT 1")
    fun elementFlow(elementId: Int): Flow<ElementEntity?>

    // Creates rows only: with id 0, SQLite picks a new id. Not OnConflictStrategy.REPLACE,
    // which on a conflict deletes the existing row instead of failing.
    @Insert
    suspend fun insert(vararg entities: ElementEntity)

    @Update
    suspend fun update(vararg entities: ElementEntity)

    @Query("DELETE FROM elements WHERE id IN (:ids)")
    suspend fun delete(vararg ids: Int)
}
