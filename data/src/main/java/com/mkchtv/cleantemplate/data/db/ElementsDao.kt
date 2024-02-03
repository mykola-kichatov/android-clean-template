package com.mkchtv.cleantemplate.data.db

import androidx.room.*
import com.mkchtv.cleantemplate.data.entity.ElementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementsDao {

    @Query("SELECT * FROM elements ORDER BY id DESC")
    fun elementsFlow(): Flow<List<ElementEntity>>

    @Query("SELECT * FROM elements WHERE id = :elementId LIMIT 1")
    fun elementFlow(elementId: Int): Flow<ElementEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entities: ElementEntity)

    @Update
    suspend fun update(vararg entities: ElementEntity)

    @Query("DELETE FROM elements WHERE id IN (:ids)")
    suspend fun delete(vararg ids: Int)

}