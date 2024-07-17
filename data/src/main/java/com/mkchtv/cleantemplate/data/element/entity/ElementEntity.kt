package com.mkchtv.cleantemplate.data.element.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "elements")
data class ElementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String
)
