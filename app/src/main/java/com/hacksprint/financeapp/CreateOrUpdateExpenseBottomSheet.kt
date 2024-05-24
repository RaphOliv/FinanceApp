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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.presentation.ExpenseUiData

class CreateOrUpdateExpenseBottomSheet(
    private val categoryList: List<CategoryEntity>,
    private val expense: ExpenseUiData? = null,
    private val onCreateClicked: (ExpenseUiData) -> Unit,
    private val onUpdateClicked: (ExpenseUiData) -> Unit,
    private val onDeleteClicked: (ExpenseUiData) -> Unit
): BottomSheetDialogFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_or_update_expense_bottom_sheet, container, false)

        val tvTitle = view.findViewById<TextView>(R.id.tv_expense_title)
        val etExpenseName = view.findViewById<TextInputEditText>(R.id.et_expense_name)
        val btnCreateOrUpdate = view.findViewById<Button>(R.id.btn_expense_create)
        val spinner: Spinner = view.findViewById(R.id.category_spinner)

        var expenseCategory : String? = null
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
            etExpenseName.setText(expense.description)

            val currentCategory = categoryList.first { it.name == expense.category }
            val index = categoryList.indexOf(currentCategory)
            spinner.setSelection(index)
        }

        btnCreateOrUpdate.setOnClickListener {
            val description = etExpenseName.text.toString().trim()
            if (expenseCategory != "Select a category" && description.isNotEmpty()) {

                if (expense == null) {
                    onCreateClicked.invoke(
                        ExpenseUiData(
                            id = 0,
                            amount = 0.0,
                            category = requireNotNull(expenseCategory),
                            date = System.currentTimeMillis().toString(),
                            description = description,

                            )
                    )
                    dismiss()
                    showMessages("Expense created")

                } else {
                    onUpdateClicked.invoke(
                        ExpenseUiData(
                            id = expense.id,
                            amount = expense.amount,
                            category = requireNotNull(expenseCategory),
                            date = expense.date,
                            description = expense.description
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
        // Configura o BottomSheet para ficar expandido

            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
    }

    private fun showMessages(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
