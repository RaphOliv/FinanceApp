package com.hacksprint.financeapp

import FinanceAppDataBase
import android.app.Application
import androidx.room.Room

class FinanceAppApplication : Application() {
    lateinit var db: FinanceAppDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            FinanceAppDataBase::class.java,
            "finance_app_database"
        ).build()
    }

    fun getFinanceAppDatabase(): FinanceAppDataBase {
        return db
    }
}
