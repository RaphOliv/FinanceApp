package com.hacksprint.financeapp.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hacksprint.financeapp.ExpenseEntity

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenseentity")
    fun getAll(): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expenseEntity: List<ExpenseEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(expenseEntity: ExpenseEntity)

    @Delete
    fun delete(expenseEntity: ExpenseEntity)
}