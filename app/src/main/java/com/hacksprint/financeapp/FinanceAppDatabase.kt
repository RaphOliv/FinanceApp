package com.hacksprint.financeapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 1)
abstract class FinanceAppDatabase : RoomDatabase(){

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getExpenseDao(): ExpenseDao

}