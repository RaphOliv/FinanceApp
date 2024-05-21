package com.hacksprint.financeapp.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 1)
abstract class FinanceAppDataBase : RoomDatabase(){

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getExpenseDao(): ExpenseDao

}