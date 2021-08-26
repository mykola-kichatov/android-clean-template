package com.mkchtv.cleantemplate.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ElementEntity(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo val name: String,
    @NonNull @ColumnInfo val description: String
)