package com.hacksprint.financeapp

import android.app.Application
import androidx.room.Room
import com.hacksprint.financeapp.data.FinanceAppDataBase

class FinanceAppApplication: Application(){

    private lateinit var db: FinanceAppDataBase

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