package com.example.roomdbtesting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [QuoteEntity::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun getQuoteDAO(): QuoteDAO

    companion object {
        private var db: QuoteDatabase? = null
        fun getQuoteDatabase(context: Context): QuoteDatabase {
            if (db == null) {
                synchronized(QuoteDatabase::class) {
                    if (db == null) db =
                        Room.databaseBuilder(context.applicationContext, QuoteDatabase::class.java, "QuoteDB")
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    Executors.newSingleThreadExecutor().execute {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            Companion.db?.getQuoteDAO()
                                                ?.insertQuote(QuoteEntity(0, "", ""))
                                        }
                                    }
                                }
                            }).allowMainThreadQueries().build()
                }
            }
            return db!!
        }
    }
}