package com.hacksprint.financeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }



    /*// Inicialize o ViewModel e o Adapter, se necessário
    viewModel = FinanceAppViewModel.create(requireActivity().application)

    expenseListAdapter = com.hacksprint.financeapp.Adapters.ExpenseListAdapter { expense ->


        Toast.makeText(requireContext(), "Expense clicked: ${expense.description}", Toast.LENGTH_SHORT).show()
    }

    //Botao azul da
    val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
    bottomSheetImageView.setOnClickListener {
        val categoryList = listOf<CategoryEntity>()
        val bottomSheetDialog = com.hacksprint.financeapp.CreateOrUpdateExpenseBottomSheet(
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
        com.hacksprint.financeapp.Adapters.ExpenseListAdapter()
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
        val createExpenseBottomSheet = com.hacksprint.financeapp.CreateOrUpdateExpenseBottomSheet(
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

