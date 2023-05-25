package com.example.roomdbtesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.roomdbtesting.db.QuoteDAO
import com.example.roomdbtesting.db.QuoteDatabase
import com.example.roomdbtesting.db.QuoteEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuoteDAOTest {

    //executes all architecture component code synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: QuoteDatabase
    lateinit var quoteDAO: QuoteDAO

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()
        quoteDAO = db.getQuoteDAO()
    }

    @Test
    fun insertQuote_expectedSingleQuote() = runBlocking {

        val mQuoteEntity = QuoteEntity(0, "This is the Test Quote", "Rajan")
        quoteDAO.insertQuote(mQuoteEntity)

        //getOrAwaitValue() blocks thread till getQuote() data
        val result = quoteDAO.getQuote().getOrAwaitValue()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("This is the Test Quote", result[0].quote)
        Assert.assertEquals("Rajan", result[0].author)
    }

    @Test
    fun deleteQuotes_expected0() = runBlocking {

        val mQuoteEntity = QuoteEntity(0, "This is the Test Quote", "Rajan")
        quoteDAO.insertQuote(mQuoteEntity)

        quoteDAO.delete()

        val result = quoteDAO.getQuote().getOrAwaitValue()

        Assert.assertEquals(0, result.size)
    }

    @After
    fun tearDown() {
        db.close()
    }
}