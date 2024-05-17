package com.hacksprint.financeapp.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hacksprint.financeapp.ExpenseEntity

@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 1)
abstract class FinanceAppDatabase : RoomDatabase(){

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getExpenseDao(): ExpenseDao

}