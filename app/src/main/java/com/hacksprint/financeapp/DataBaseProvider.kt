/*package com.hacksprint.financeapp

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database: FinanceAppDatabase? = null

    fun initializeDatabase(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            FinanceAppDatabase::class.java,
            "finance_app_database"
        ).build()
    }

    fun getDatabase(): FinanceAppDatabase {
        return database ?: throw IllegalStateException("Database not initialized")
    }
}*/