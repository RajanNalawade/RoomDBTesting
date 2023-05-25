package com.example.roomdbtesting.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuoteDAO {
    @Insert
    suspend fun insertQuote(mQuoteEntity: QuoteEntity)

    @Update
    suspend fun updateQuote(mQuoteEntity: QuoteEntity)

    @Delete
    suspend fun deleteQuote(mQuoteEntity: QuoteEntity)

    @Query("SELECT * FROM QuoteEntity")
    fun getQuote() : LiveData<List<QuoteEntity>>

    @Query("DELETE FROM QuoteEntity")
    suspend fun delete()

    @Query("SELECT * FROM QuoteEntity WHERE id = :quoteID")
    fun getQuoteByID(quoteID : Int) : LiveData<QuoteEntity>
}