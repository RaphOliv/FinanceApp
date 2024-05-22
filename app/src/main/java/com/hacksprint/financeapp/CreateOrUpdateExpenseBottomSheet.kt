package com.hacksprint.financeapp

import FinanceAppViewModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.hacksprint.financeapp.Adapters.ExpenseListAdapter
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseUiData
import java.text.SimpleDateFormat
import java.util.Calendar

class CreateOrUpdateExpenseBottomSheet(
    private val viewModelFinance: FinanceAppViewModel,
    private val adapterFinance: ExpenseListAdapter,
    private val categoryList: List<CategoryEntity>,
    private val expense: ExpenseUiData? = null,
    private val onCreateClicked: (ExpenseUiData) -> Unit,
    private val onUpdateClicked: (ExpenseUiData) -> Unit,
    private val onDeleteClicked: (ExpenseUiData) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var edtExpenseName: TextInputEditText
    private lateinit var edtExpenseAmount: TextInputEditText
    private lateinit var edtExpenseDate: TextInputEditText
    private lateinit var btnCreateOrUpdate: Button
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_or_update_expense_bottom_sheet, container, false)

        // Inicialize os EditTexts e o Button
        edtExpenseName = view.findViewById(R.id.et_expense_name)
        edtExpenseAmount = view.findViewById(R.id.et_expense_amount)
        edtExpenseDate = view.findViewById(R.id.et_expense_date)
        btnCreateOrUpdate = view.findViewById(R.id.btn_expense_create)
        spinner = view.findViewById(R.id.category_spinner)

        // Configuração do Spinner
        val categoryNames = categoryList.map { it.name }
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = categoryAdapter

        // Preenchendo os EditTexts se a despesa existir
        expense?.let {
            edtExpenseName.setText(it.description)
            edtExpenseAmount.setText(it.amount.toString())
            edtExpenseDate.setText(it.date)
            val categoryIndex = categoryNames.indexOf(it.category)
            spinner.setSelection(categoryIndex)
        }

        // Listener para o EditText de data
        edtExpenseDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Listener para o botão de salvar
        btnCreateOrUpdate.setOnClickListener {
            val description = edtExpenseName.text.toString().trim()
            val amount = edtExpenseAmount.text.toString().toDoubleOrNull() ?: 0.0
            val date = edtExpenseDate.text.toString()
            val category = spinner.selectedItem.toString()

            if (description.isNotBlank() && amount > 0 && date.isNotBlank() && category.isNotBlank()) {
                val expenseData = ExpenseUiData(expense?.id ?: 0, description, amount, category, date)
                if (expense == null) {
                    onCreateClicked.invoke(expenseData)
                } else {
                    onUpdateClicked.invoke(expenseData)
                }
                dismiss()
            } else {
                showToast("Please fill all fields")
            }
        }

        return view
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate = dateFormat.format(selectedDate.time)
                edtExpenseDate.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
    override fun onStart() {
        super.onStart()


        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Configure o BottomSheetBehavior para estar totalmente expandido
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? ViewGroup
        val behavior = bottomSheet?.let { BottomSheetBehavior.from(it) }
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        behavior?.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
