package com.hacksprint.financeapp.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Dao
import com.hacksprint.financeapp.FinanceAppApplication
import com.hacksprint.financeapp.data.CategoryDao
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseDao
import com.hacksprint.financeapp.data.ExpenseEntity
import java.util.Locale

class FinanceAppViewModel(private val categoryDao: CategoryDao, private val expenseDao: ExpenseDao): ViewModel(){

    val categoryListLiveDataLiveData: LiveData<List<CategoryEntity>> = categoryDao.getAll()
    val expenseListLiveData: LiveData<List<ExpenseEntity>> = expenseDao.getAll()



    companion object {
        fun create(application: Application): FinanceAppViewModel {
            val dataBaseInstance = (application as FinanceAppApplication).getFinanceAppDatabase()
            val categorydao = dataBaseInstance.getCategoryDao()
            val expensedao = dataBaseInstance.getExpenseDao()
            return FinanceAppViewModel(categorydao, expensedao)
        }
    }

}