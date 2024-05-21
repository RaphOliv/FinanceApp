package com.hacksprint.financeapp.DataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hacksprint.financeapp.DataBase.CategoryEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["key"],
            childColumns = ["category"],
        )
    ]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val category: String,
    val description: String,
    val date: Long
)
