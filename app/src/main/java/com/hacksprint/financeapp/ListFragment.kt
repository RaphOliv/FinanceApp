package com.hacksprint.financeapp

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseEntity
import com.hacksprint.financeapp.data.FinanceAppDataBase
import com.hacksprint.financeapp.presentation.CategoryListAdapter
import com.hacksprint.financeapp.presentation.CategoryUiData
import com.hacksprint.financeapp.presentation.ExpenseListAdapter
import com.hacksprint.financeapp.presentation.ExpenseUiData
import com.hacksprint.financeapp.presentation.FinanceAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    /*private var categories = listOf<CategoryUiData>()
    private var expenses = listOf<ExpenseUiData>()
    private val categoryAdapter = CategoryListAdapter()
    private lateinit var onDeleteClicked: (ExpenseUiData) -> Unit
    private var categoriesEntity = listOf<CategoryEntity>()
    private lateinit var onCreateClicked: (ExpenseUiData) -> Unit
    private val expenseAdapter by lazy {
        ExpenseListAdapter()
    }

    private var rvCategory: RecyclerView? = null
    private var rvExpenses: RecyclerView? = null

    private lateinit var ctnContent: LinearLayout

    private val viewModel: FinanceAppViewModel by lazy {
        FinanceAppViewModel.create(application = requireActivity().application)
    }


    lateinit var db: FinanceAppDataBase

    private val categoryDao by lazy {
        db.getCategoryDao()
    }

    private val expenseDao by lazy {
        db.getExpenseDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategory = view.findViewById(R.id.rv_categories)
        rvExpenses = view.findViewById(R.id.rv_expenses)

        val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!
        val swipeBackground = ColorDrawable(Color.RED)

        val openCategoryBottomSheet = view.findViewById<ImageView>(R.id.btn_add_category)
        *//*ctnContent = view.findViewById(R.id.ctn_content)
        ctnContent.visibility = View.GONE*//*

        *//*openCategoryBottomSheet.setOnClickListener {
            val bottomSheetFragment = CreateCategoryBottomSheet(
                onCreateClicked = { category ->
                    val categoryListAdapter = rvCategory?.adapter as? CategoryListAdapter
                    categoryListAdapter?.submitList(
                        categoryListAdapter.currentList.toMutableList().apply {
                            add(CategoryUiData(category))
                        }
                    )

                }
            )
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

        }*//*

        expenseAdapter.setOnClickListener {expense ->
            showUpdateExpenseBottomSheet(expense)
        }


        openCategoryBottomSheet.setOnClickListener {
            val createCategoryBottomSheet = CreateCategoryBottomSheet{ categoryName ->
                val categoryEntity = CategoryEntity(
                    name = categoryName,
                    isSelected = false
                )

                insertCategory(categoryEntity)
            }
            createCategoryBottomSheet.show(childFragmentManager, createCategoryBottomSheet.tag)
        }

        categoryAdapter.setOnLongClickListener { categoryToBeDeleted ->
            if(*//*categoryToBeDeleted.name != "+" && *//*categoryToBeDeleted.name != "ALL") {
                val title = this.getString(R.string.category_delete_title)
                val message = this.getString(R.string.category_delete_message)
                val btnAction = this.getString(R.string.delete)

                showInfoDialog(
                    title = title,
                    message = message,
                    action = btnAction,
                ) {
                    val categoryEntityToBeDeleted = CategoryEntity(
                        name = categoryToBeDeleted.name,
                        isSelected = categoryToBeDeleted.isSelected
                    )
                    deleteCategory(categoryEntityToBeDeleted)
                }
            }
        }

        categoryAdapter.setOnClickListener { selected ->
            if (selected.name == "") {
               *//* val createCategoryBottomSheet = CreateCategoryBottomSheet{ categoryName ->
                    val categoryEntity = CategoryEntity(
                        name = categoryName,
                        isSelected = false
                    )

                    insertCategory(categoryEntity)
                }
                createCategoryBottomSheet.show(childFragmentManager, createCategoryBottomSheet.tag)*//*

            } else {
                val categoryTemp = categories.map { item ->
                    when {
                        item.name == selected.name && item.isSelected -> item.copy(isSelected = true)

                        item.name == selected.name && !item.isSelected -> item.copy(isSelected = true)
                        item.name != selected.name && item.isSelected -> item.copy(isSelected = false)
                        else -> item
                    }
                }

                if (selected.name != "ALL") {
                    filterExpensesByCategoryName(selected.name)
                } else {
                    GlobalScope.launch(Dispatchers.IO) {
                        getExpensesFromDatabase()
                    }
                }

                categoryAdapter.submitList(categoryTemp)
            }

        }

        rvCategory?.adapter = categoryAdapter
        rvExpenses?.adapter = expenseAdapter

        onDeleteClicked = { expense ->
            val expenseEntityToBeDeleted = ExpenseEntity(
                id = expense.id.toLong(),
                amount = expense.amount,
                description = expense.description,
                category = expense.category,
                date = System.currentTimeMillis(),
                *//*icon = expense.icon,
                status = expense.status*//*
            )
            deleteExpense(expenseEntityToBeDeleted)
            Toast.makeText(requireContext(), "Expense deleted", Toast.LENGTH_SHORT).show()
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val task = expenses[position]
                    onDeleteClicked.invoke(task)

                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )

                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                    val iconTop =
                        itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                    val iconBottom = iconTop + deleteIcon.intrinsicHeight

                    if (dX < 0) {
                        val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                        val iconRight = itemView.right - iconMargin
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                        swipeBackground.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                    } else {
                        swipeBackground.setBounds(0, 0, 0, 0)
                        deleteIcon.setBounds(0, 0, 0, 0)
                    }

                    swipeBackground.draw(c)
                    deleteIcon.draw(c)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvExpenses)
    }

    override fun onStart() {
        super.onStart()

        *//*db = (application as FinanceAppApplication).getFinanceAppDatabase()*//*

        GlobalScope.launch(Dispatchers.IO) {
            getCategoriesFromDatabase()
        }

        GlobalScope.launch(Dispatchers.IO) {
            getExpensesFromDatabase()
        }

    }

    private fun showInfoDialog(
        title: String,
        message: String,
        action: String,
        onActionClicked: () -> Unit
    ) {
        val infoBottomSheet = InfoBottomSheet(
            title = title,
            message = message,
            action = action,
            onActionClicked = onActionClicked
        )
        infoBottomSheet.show(
            requireActivity().supportFragmentManager,
            "infoBottomSheet"
        )
    }

    private fun getCategoriesFromDatabase() {
        val categoryObserver = Observer<List<CategoryEntity>> { categoriesFromDb ->
            categoriesEntity = categoriesFromDb
            val categoriesUiData = categoriesFromDb.map {
                CategoryUiData(
                    name = it.name,
                    isSelected = it.isSelected
                )
            }
                .toMutableList()

            *//*categoriesUiData.add(
                CategoryUiData(
                    name = "+",
                    isSelected = false
                )
            )*//*

            val tempCategoryList = mutableListOf(
                CategoryUiData(
                    name = "ALL",
                    isSelected = true
                )
            )

            tempCategoryList.addAll(categoriesUiData)
            categoryAdapter.submitList(tempCategoryList)
        }

        viewModel.categoryListLiveDataLiveData.observe(viewLifecycleOwner, categoryObserver)

    }


    private fun getExpensesFromDatabase() {
        val expenseObserver = Observer<List<ExpenseEntity>> { expensesFromDb ->
            if (expensesFromDb.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
            } else {
                ctnContent.visibility = View.GONE
            }
            expenseAdapter.submitList(expensesFromDb.map {
                ExpenseUiData(
                    id = it.id.toInt(),
                    amount = it.amount,
                    category = it.category,
                    description = it.description,
                    date = it.date.toString(),
                    *//*icon = it.icon,
                    status = it.status*//*
                )
            })

        }

        viewModel.expenseListLiveData.observe(viewLifecycleOwner, expenseObserver)

        *//*GlobalScope.launch(Dispatchers.Main) {
            expenses = expensesUiData
            expenseAdapter.submitList(expensesUiData)
        }*//*
    }



    private fun insertCategory(categoryEntity: CategoryEntity){
        GlobalScope.launch(Dispatchers.IO) {
            categoryDao.insert(categoryEntity)
        }
    }

    private fun updateExpense(expenseEntity: ExpenseEntity){
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.update(expenseEntity)
        }
    }

    private fun deleteExpense(expenseEntity: ExpenseEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.delete(expenseEntity)
        }
    }

    private fun deleteCategory(categoryEntity: CategoryEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            val expensesToBeDeleted = expenseDao.getAllByCategoryName(categoryEntity.name)
            expenseDao.deleteAll(expensesToBeDeleted)
            categoryDao.delete(categoryEntity)
        }
    }

    private fun filterExpensesByCategoryName(categoryName: String){
        GlobalScope.launch(Dispatchers.IO) {
            val expensesFromDb: List<ExpenseEntity> = expenseDao.getAllByCategoryName(categoryName)
            val expensesUiData = expensesFromDb.map {
                ExpenseUiData(
                    id = it.id.toInt(),
                    amount = it.amount,
                    category = it.category,
                    description = it.description,
                    date = it.date.toString(),
                    *//*icon = it.icon,
                    status = it.status*//*
                )
            }

            GlobalScope.launch(Dispatchers.Main) {
                expenseAdapter.submitList(expensesUiData)
            }
        }
    }

    private fun showUpdateExpenseBottomSheet(expenseUiData: ExpenseUiData? = null) {
        val createExpenseBottomSheet = CreateOrUpdateExpenseBottomSheet(
            expense = expenseUiData,
            categoryList = categoriesEntity,
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
            },
            onDeleteClicked = onDeleteClicked,
            onCreateClicked = onCreateClicked
        )
        createExpenseBottomSheet.show(
            requireActivity().supportFragmentManager,
            createExpenseBottomSheet.tag
        )
    }*/

}