package com.hacksprint.financeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseDao
import com.hacksprint.financeapp.data.ExpenseEntity
import com.hacksprint.financeapp.data.FinanceAppDataBase
import com.hacksprint.financeapp.presentation.ExpenseListAdapter
import com.hacksprint.financeapp.presentation.ExpenseUiData
import com.hacksprint.financeapp.presentation.FinanceAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: FinanceAppViewModel
    private lateinit var expenseListAdapter: ExpenseListAdapter

    private lateinit var db: FinanceAppDataBase
    private lateinit var expenseDao: ExpenseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
        bottomSheetImageView.setOnClickListener {
            val categoryList = listOf<CategoryEntity>()
            val bottomSheetDialog = CreateOrUpdateExpenseBottomSheet(
                categoryList,
                onCreateClicked = { expense ->
                    val expenseEntity = ExpenseEntity(
                        id = 0,
                        amount = expense.amount,
                        category = expense.category,
                        description = expense.description,
                        date = System.currentTimeMillis()
                    )
                    GlobalScope.launch(Dispatchers.IO) {
                        val expenseDao = db.getExpenseDao()
                        expenseDao.insert(expenseEntity)
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Expense Created", Toast.LENGTH_SHORT).show()
                        }
                    }

                    /*viewModel.expenseListLiveData.observe(viewLifecycleOwner, Observer { expenseList ->
                        expenseListAdapter.submitList(expenseList.map { ExpenseUiData(it) })
                    })*/


                },
                onUpdateClicked = { expense ->
                    // Lógica para atualizar uma despesa existente
                },
                onDeleteClicked = { expense ->
                    // Lógica para excluir uma despesa existente
                }
            )
            bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
        }

        return view
    }



    /*// Inicialize o ViewModel e o Adapter, se necessário
    viewModel = FinanceAppViewModel.create(requireActivity().application)

    expenseListAdapter = ExpenseListAdapter { expense ->


        Toast.makeText(requireContext(), "Expense clicked: ${expense.description}", Toast.LENGTH_SHORT).show()
    }

    //Botao azul da
    val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
    bottomSheetImageView.setOnClickListener {
        val categoryList = listOf<CategoryEntity>()
        val bottomSheetDialog = CreateOrUpdateExpenseBottomSheet(
            viewModel,
            expenseListAdapter,
            categoryList,
            onCreateClicked = { expense ->
                // Lógica para criar uma nova despesa
            },
            onUpdateClicked = { expense ->
                // Lógica para atualizar uma despesa existente
            },
            onDeleteClicked = { expense ->
                // Lógica para excluir uma despesa existente
            }
        )
        bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
    }

    return view*/
    }

   /*private var categoriesEntity = listOf<CategoryEntity>()
    private lateinit var onDeleteClicked: (ExpenseUiData) -> Unit

    private val expenseAdapter by lazy {
        ExpenseListAdapter()
    }

    lateinit var db: FinanceAppDataBase

    private val expenseDao by lazy {
        db.getExpenseDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        *//*val rvExpenses = view.findViewById<RecyclerView>(R.id.rv_expenses)
        rvExpenses.layoutManager = LinearLayoutManager(requireContext())
        rvExpenses.adapter = expenseAdapter*//*
        val btnCreateExpense = view.findViewById<ImageView>(R.id.btn_show_dielog)

        btnCreateExpense.setOnClickListener {
            showCreateUpdateExpenseBottomSheet()
        }

       *//* expenseAdapter.setOnClickListener {expense ->
            showCreateUpdateExpenseBottomSheet(expense)

        }*//*

        return view
    }
    private fun insertExpense(expenseEntity: ExpenseEntity){
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.insert(expenseEntity)
        }
    }

    private fun updateExpense(expenseEntity: ExpenseEntity){
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.update(expenseEntity)
        }
    }


    private fun showCreateUpdateExpenseBottomSheet(expenseUiData: ExpenseUiData? = null) {
        val createExpenseBottomSheet = CreateOrUpdateExpenseBottomSheet(
            expense = expenseUiData,
            categoryList = categoriesEntity,
            onCreateClicked = {
                    expenseToBeCreated ->
                val expenseEntityToBeInserted = ExpenseEntity(
                    amount = expenseToBeCreated.amount,
                    category = expenseToBeCreated.category,
                    description = expenseToBeCreated.description,
                    date = System.currentTimeMillis(),
                    *//*icon = R.drawable.ic_home,
                    status = R.drawable.baseline_circle_green_24*//*
                )
                insertExpense(expenseEntityToBeInserted)
                Toast.makeText(requireContext(), "Expense Created", Toast.LENGTH_SHORT).show()
            },
            onUpdateClicked = {
                    expenseToBeUpdated ->
                val expenseEntityToBeUpdated = ExpenseEntity(
                    id = expenseToBeUpdated.id.toLong(),
                    amount = expenseToBeUpdated.amount,
                    category = expenseToBeUpdated.category,
                    description = expenseToBeUpdated.description,
                    date = System.currentTimeMillis(),
                    *//*icon = R.drawable.ic_home,
                    status = R.drawable.baseline_circle_green_24*//*
                )
                updateExpense(expenseEntityToBeUpdated)
                Toast.makeText(requireContext(), "Expense Updated", Toast.LENGTH_SHORT).show()
            },
            onDeleteClicked = onDeleteClicked
        )
        createExpenseBottomSheet.show(
            requireActivity().supportFragmentManager,
            "createExpenseBottomSheet"
        )
    }*/

