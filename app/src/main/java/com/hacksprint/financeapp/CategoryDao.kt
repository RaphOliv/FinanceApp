package com.hacksprint.financeapp

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface CategoryDao {
    @Query("SELECT * FROM categoryentity")
    fun getAll(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryEntity: List<CategoryEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categoryEntity: CategoryEntity)

    @Delete
    fun delete(categoryEntity: CategoryEntity)
}