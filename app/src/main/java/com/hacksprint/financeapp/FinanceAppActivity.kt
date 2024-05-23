package com.hacksprint.financeapp.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.hacksprint.financeapp.CreateCategoryBottomSheet
import com.hacksprint.financeapp.CreateOrUpdateExpenseBottomSheet
import com.hacksprint.financeapp.InfoBottomSheet
import com.hacksprint.financeapp.R
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseEntity
import com.hacksprint.financeapp.data.FinanceAppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.E

class FinanceAppActivity : AppCompatActivity() {
    private var categories = listOf<CategoryUiData>()
    private var expenses = listOf<ExpenseUiData>()
    private var categoriesEntity = listOf<CategoryEntity>()
    private lateinit var onDeleteClicked: (ExpenseUiData) -> Unit

    private val categoryAdapter = CategoryListAdapter()
    private val expenseAdapter by lazy {
        ExpenseListAdapter()
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FinanceAppDataBase::class.java, "database-financeapp"
        ).build()
    }

    private val categoryDao by lazy {
        db.getCategoryDao()
    }

    private val expenseDao by lazy {
        db.getExpenseDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list,)

        val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)
        val rvExpense = findViewById<RecyclerView>(R.id.rv_expenses)
        /*val fabCreateExpense = findViewById<FloatingActionButton>(R.id.fab_create_expense)*/

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!
        val swipeBackground = ColorDrawable(Color.RED)

        /* fabCreateExpense.setOnClickListener {
             showCreateUpdateExpenseBottomSheet()
         }*/

        expenseAdapter.setOnClickListener {expense ->
            showCreateUpdateExpenseBottomSheet(expense)
        }

        categoryAdapter.setOnLongClickListener { categoryToBeDeleted ->
            if(categoryToBeDeleted.name != "+" && categoryToBeDeleted.name != "ALL") {
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
                    Toast.makeText(this, "Category deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        categoryAdapter.setOnClickListener { selected ->
            if (selected.name == "+") {
                val createCategoryBottomSheet = CreateCategoryBottomSheet{ categoryName ->
                    val categoryEntity = CategoryEntity(
                        name = categoryName,
                        isSelected = false
                    )

                    insertCategory(categoryEntity)
                }
                createCategoryBottomSheet.show(supportFragmentManager, "create_category")

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

        rvCategory.adapter = categoryAdapter
        GlobalScope.launch(Dispatchers.IO) {
            getCategoriesFromDatabase()
        }

        rvExpense.adapter = expenseAdapter
        GlobalScope.launch(Dispatchers.IO) {
            getExpensesFromDatabase()
        }

        onDeleteClicked = { expense ->
            val expenseEntityToBeDeleted = ExpenseEntity(
                id = expense.id.toLong(),
                amount = expense.amount,
                description = expense.description,
                category = expense.category,
                date = System.currentTimeMillis(),
                /*icon = expense.icon,
                status = expense.status*/
            )
            deleteExpense(expenseEntityToBeDeleted)
            Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show()
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
        itemTouchHelper.attachToRecyclerView(rvExpense)
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
            supportFragmentManager,
            "infoBottomSheet")
    }

    private fun getCategoriesFromDatabase() {
        categoryDao.getAll().observe(this) { categoriesFromDb ->
            categoriesEntity = categoriesFromDb
            val categoriesUiData = categoriesFromDb.map {
                CategoryUiData(
                    name = it.name,
                    isSelected = it.isSelected
                )
            }.toMutableList()

            categoriesUiData.add(
                CategoryUiData(
                    name = "+",
                    isSelected = false
                )
            )

            val tempCategoryList = mutableListOf(
                CategoryUiData(
                    name = "ALL",
                    isSelected = true
                )
            )

            tempCategoryList.addAll(categoriesUiData)

            categories = tempCategoryList
            categoryAdapter.submitList(categories)
        }
    }



    private fun getExpensesFromDatabase() {
        expenseDao.getAll().observe(this) { expensesFromDb ->
            val expensesUiData = expensesFromDb.map {
                ExpenseUiData(
                    id = it.id.toInt(),
                    amount = it.amount,
                    category = it.category,
                    description = it.description,
                    date = it.date.toString(),
                    /*icon = it.icon,
                    status = it.status*/
                )
            }

            expenses = expensesUiData
            expenseAdapter.submitList(expensesUiData)
        }
    }


    private fun insertCategory(categoryEntity: CategoryEntity){
        GlobalScope.launch(Dispatchers.IO) {
            categoryDao.insert(categoryEntity)
            getCategoriesFromDatabase()
        }
    }

    private fun insertExpense(expenseEntity: ExpenseEntity){
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.insert(expenseEntity)
            getExpensesFromDatabase()
        }
    }

    private fun updateExpense(expenseEntity: ExpenseEntity){
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.update(expenseEntity)
            getExpensesFromDatabase()
        }
    }

    private fun deleteExpense(expenseEntity: ExpenseEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.delete(expenseEntity)
            getExpensesFromDatabase()
        }
    }

    private fun deleteCategory(categoryEntity: CategoryEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            val expensesToBeDeleted = expenseDao.getAllByCategoryName(categoryEntity.name)
            expenseDao.deleteAll(expensesToBeDeleted)
            categoryDao.delete(categoryEntity)
            getCategoriesFromDatabase()
            getExpensesFromDatabase()
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
                    /*icon = it.icon,
                    status = it.status*/
                )
            }

            GlobalScope.launch(Dispatchers.Main) {
                expenseAdapter.submitList(expensesUiData)
            }
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
                    /*icon = R.drawable.ic_home,
                    status = R.drawable.baseline_circle_green_24*/
                )
                insertExpense(expenseEntityToBeInserted)
            },
            onUpdateClicked = {
                    expenseToBeUpdated ->
                val expenseEntityToBeUpdated = ExpenseEntity(
                    id = expenseToBeUpdated.id.toLong(),
                    amount = expenseToBeUpdated.amount,
                    category = expenseToBeUpdated.category,
                    description = expenseToBeUpdated.description,
                    date = System.currentTimeMillis(),
                    /*icon = R.drawable.ic_home,
                    status = R.drawable.baseline_circle_green_24*/
                )
                updateExpense(expenseEntityToBeUpdated)
            },
            onDeleteClicked = onDeleteClicked,


        )
        createExpenseBottomSheet.show(
            supportFragmentManager,
            "create_expense")
    }
}