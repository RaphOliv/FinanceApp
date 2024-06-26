package com.hacksprint.financeapp

import android.app.DatePickerDialog
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
import com.hacksprint.financeapp.Adapters.ExpenseListAdapter
import com.hacksprint.financeapp.Adapters.ListIconsAdapter
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.Adapters.ExpenseUiData
import java.util.Calendar

class CreateOrUpdateExpenseBottomSheet(
    private val categoryList: List<CategoryEntity>,
    private val expenseList: MutableList<ExpenseUiData>?, // Referência à lista de despesas
    private val expense: ExpenseUiData? = null,
    private val onCreateClicked: (ExpenseUiData) -> Unit,
    private val onUpdateClicked: (ExpenseUiData) -> Unit,
    private val onDeleteClicked: ((ExpenseUiData) -> Unit)? = null
) : BottomSheetDialogFragment(), ListIconsAdapter.IconClickListener {

    private var selectedIconResId: Int = R.drawable.ic_home // Ícone padrão selecionado

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_or_update_expense_bottom_sheet, container, false)

        // Initialize RecyclerView and Adapter
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
        val listIconsAdapter = ListIconsAdapter(icons, this) // Passa esta classe como ouvinte de clique de ícone
        recyclerViewIcons.adapter = listIconsAdapter
        recyclerViewIcons.layoutManager = GridLayoutManager(context, 5)

        // Initialize views
        val tvTitle = view.findViewById<TextView>(R.id.tv_expense_title)
        val edtExpenseName = view.findViewById<TextInputEditText>(R.id.et_expense_name)
        val edtExpenseAmount = view.findViewById<TextInputEditText>(R.id.et_expense_amount)
        val edtExpenseDate = view.findViewById<TextInputEditText>(R.id.et_expense_date)
        val btnCreateOrUpdate = view.findViewById<Button>(R.id.btn_expense_create)
        val spinner: Spinner = view.findViewById(R.id.category_spinner)

        var expenseCategory: String? = null
        val categoryListTemp = mutableListOf("Select a category")

        edtExpenseDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                edtExpenseDate.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

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

            val index = categoryListTemp.indexOf(expense.category)
            if (index != -1) {
                spinner.setSelection(index)
            }
        }

        btnCreateOrUpdate.setOnClickListener {
            val name = edtExpenseName.text.toString()
            val amount = edtExpenseAmount.text.toString()
            val date = edtExpenseDate.text.toString()
            val category = expenseCategory
            val iconResId = selectedIconResId // Adicione esta linha para obter o ID do ícone selecionado

            if (name.isEmpty() || amount.isEmpty() || date.isEmpty() || category == null ) {
                showMessages("All fields are required")
                return@setOnClickListener
            }

            val expenseData = ExpenseUiData(
                id = expense?.id ?: 0,
                description = name,
                amount = amount.toDouble(),
                date = date,
                category = category,
                iconResId = iconResId // Passe o ID do ícone selecionado para ExpenseUiData
            )

            if (expense == null) {
                onCreateClicked.invoke(expenseData)
            } else {
                onUpdateClicked(expenseData)
            }

            // Atualizar a lista de despesas
            if (expense == null) {
                // Se é uma nova despesa, adicione à lista
                expenseList?.add(expenseData)
            } else {
                // Se é uma atualização, encontre e substitua na lista
                val index = expenseList?.indexOfFirst { it.id == expenseData.id }
                if (index != -1) {
                    if (index != null) {
                        expenseList?.set(index, expenseData)
                    }
                }
            }

            // Atualizar a lista de despesas no adaptador
            (recyclerViewIcons.adapter as? ExpenseListAdapter)?.submitList(expenseList?.toList())

            dismiss()
        }


        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Configure BottomSheetBehavior to be fully expanded
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

    override fun onIconClicked(iconResId: Int) {
        selectedIconResId = iconResId // Atualiza o ícone selecionado
    }
}
