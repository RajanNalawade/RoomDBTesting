package com.example.roomdbtesting.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val quote : String,
    val author : String
)