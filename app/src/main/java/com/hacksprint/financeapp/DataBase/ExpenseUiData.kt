package com.hacksprint.financeapp.DataBase

import androidx.annotation.DrawableRes

data class ExpenseUiData(
    val id: Int,
    val name: String,
    val amount: Double,
    val category: String,
    val date: String,
    @DrawableRes val icon: Int,
    @DrawableRes val status: Int
)
