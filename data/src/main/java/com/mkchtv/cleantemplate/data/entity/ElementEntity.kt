package com.mkchtv.cleantemplate.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "elements")
data class ElementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo val name: String,
    @NonNull @ColumnInfo val description: String
)