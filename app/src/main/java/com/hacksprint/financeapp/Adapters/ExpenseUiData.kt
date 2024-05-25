package com.hacksprint.financeapp.Adapters

//data class para o btnDielog createOrUpdateExpenseBottomSheet
data class ExpenseUiData(
    val id: Int,
    val description: String,
    val amount: Double,
    val category: String,
    val iconResId: Int,
    val date: String
) {

}
