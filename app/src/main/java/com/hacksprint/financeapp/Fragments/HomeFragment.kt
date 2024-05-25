package com.hacksprint.financeapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hacksprint.financeapp.Adapters.ExpenseListAdapter
import com.hacksprint.financeapp.Adapters.ExpenseUiData
import com.hacksprint.financeapp.CreateOrUpdateExpenseBottomSheet
import com.hacksprint.financeapp.R
import com.hacksprint.financeapp.data.CategoryEntity

class HomeFragment : Fragment() {

    private lateinit var expenseListAdapter: ExpenseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        expenseListAdapter = ExpenseListAdapter { expense ->
            Toast.makeText(requireContext(), "Expense clicked", Toast.LENGTH_SHORT).show()
        }

        val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
        bottomSheetImageView.setOnClickListener {
            // Criando uma lista vazia de despesas por enquanto
            val expenseList = mutableListOf<ExpenseUiData>()

            val categoryList = listOf<CategoryEntity>()
            val bottomSheetDialog = CreateOrUpdateExpenseBottomSheet(
                categoryList,
                null, // Aqui você pode passar null para expense, já que não está atualizando ou criando uma despesa no momento
                onCreateClicked = { expenseData ->
                    // Lógica para criar uma nova despesa
                    expenseList.add(expenseData)
                    expenseListAdapter.submitList(expenseList)
                },
                onUpdateClicked = { expenseData ->
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
}
