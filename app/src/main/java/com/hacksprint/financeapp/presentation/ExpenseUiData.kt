package com.hacksprint.financeapp.presentation

import androidx.annotation.DrawableRes

data class ExpenseUiData(
    val id: Int,
    val description: String,
    val amount: Double,
    val category: String,
    val date: String
)
