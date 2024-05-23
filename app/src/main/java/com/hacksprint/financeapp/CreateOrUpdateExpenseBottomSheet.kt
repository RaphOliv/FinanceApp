package com.hacksprint.financeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.hacksprint.financeapp.Adapters.ListIconsAdapter
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.presentation.ExpenseUiData

class CreateOrUpdateExpenseBottomSheet(
    private val categoryList: List<CategoryEntity>,
    private val expense: ExpenseUiData? = null,
    private val onCreateClicked: (ExpenseUiData) -> Unit,
    private val onUpdateClicked: (ExpenseUiData) -> Unit,
    private val onDeleteClicked: ((ExpenseUiData) -> Unit)? = null
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_or_update_expense_bottom_sheet, container, false)

        // Inicialize o RecyclerView e o Adapter
        val recyclerViewIcons = view.findViewById<RecyclerView>(R.id.recyclerListIcons)
        val icons = listOf(
            R.drawable.ic_home,
            R.drawable.baseline_wifi_24,
            R.drawable.baseline_water_drop_24,
            R.drawable.baseline_videogame_asset_24,
            R.drawable.baseline_shopping_cart_24,
            R.drawable.baseline_local_gas_station_24,
            R.drawable.baseline_local_airport_24,
            R.drawable.baseline_electric_bolt_24,
            R.drawable.baseline_directions_car_24,
            R.drawable.baseline_credit_card_24,
            R.drawable.baseline_bar_chart_24
        )
        val listIconsAdapter = ListIconsAdapter(icons)
        recyclerViewIcons.adapter = listIconsAdapter
        recyclerViewIcons.layoutManager = GridLayoutManager(context, 5)

        // Initialize the views
        val tvTitle = view.findViewById<TextView>(R.id.tv_expense_title)
        val edtExpenseName = view.findViewById<TextInputEditText>(R.id.et_expense_name)
        val edtExpenseAmount = view.findViewById<TextInputEditText>(R.id.et_expense_amount)
        val edtExpenseDate = view.findViewById<TextInputEditText>(R.id.et_expense_date)
        val btnCreateOrUpdate = view.findViewById<Button>(R.id.btn_expense_create)
        val spinner: Spinner = view.findViewById(R.id.category_spinner)

        var expenseCategory: String? = null
        val categoryListTemp = mutableListOf("Select a category")
        categoryListTemp.addAll(categoryList.map { it.name })

        ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            categoryListTemp.toList()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                expenseCategory = categoryListTemp[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showMessages("Category is required")
            }
        }

        if (expense == null) {
            tvTitle.setText(R.string.create_expense_title)
            btnCreateOrUpdate.setText(R.string.create)
        } else {
            tvTitle.setText(R.string.update_expense_title)
            btnCreateOrUpdate.setText(R.string.update)
            edtExpenseName.setText(expense.description)
            edtExpenseAmount.setText(expense.amount.toString())
            edtExpenseDate.setText(expense.date)

            val currentCategory = categoryList.first { it.name == expense.category }
            val index = categoryList.indexOf(currentCategory)
            spinner.setSelection(index)
        }

        btnCreateOrUpdate.setOnClickListener {
            val description = edtExpenseName.text.toString().trim()
            if (expenseCategory != "Select a category" && description.isNotEmpty()) {
                if (expense == null) {
                    onCreateClicked.invoke(
                        ExpenseUiData(
                            id = 0,
                            amount = edtExpenseAmount.text.toString().toDoubleOrNull() ?: 0.0,
                            category = requireNotNull(expenseCategory),
                            date = System.currentTimeMillis().toString(),
                            description = description
                        )
                    )
                    dismiss()
                    showMessages("Expense created")
                } else {
                    onUpdateClicked.invoke(
                        ExpenseUiData(
                            id = expense.id,
                            amount = edtExpenseAmount.text.toString().toDoubleOrNull() ?: expense.amount,
                            category = requireNotNull(expenseCategory),
                            date = expense.date,
                            description = description
                        )
                    )
                    dismiss()
                    showMessages("Expense updated")
                }
            } else {
                showMessages("Fields are required")
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        // Certifique-se de que o layout da janela seja MATCH_PARENT
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Configure o BottomSheetBehavior para estar totalmente expandido
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? ViewGroup
        val behavior = bottomSheet?.let { BottomSheetBehavior.from(it) }
        if (behavior != null) {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        if (behavior != null) {
            behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        }
    }

    private fun showMessages(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
