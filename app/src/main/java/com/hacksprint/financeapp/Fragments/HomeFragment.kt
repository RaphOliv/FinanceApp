package com.hacksprint.financeapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hacksprint.financeapp.Adapters.ExpenseListAdapter
import com.hacksprint.financeapp.CreateOrUpdateExpenseBottomSheet
import com.hacksprint.financeapp.R
import com.hacksprint.financeapp.data.CategoryEntity

class HomeFragment : Fragment() {

   /* private lateinit var viewModel: FinanceAppViewModel*/
    private lateinit var expenseListAdapter: ExpenseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        /*viewModel = FinanceAppViewModel.create(requireActivity().application)*/

        expenseListAdapter = ExpenseListAdapter { expense ->
            Toast.makeText(requireContext(), "Expense clicked", Toast.LENGTH_SHORT).show()
        }

        val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
        bottomSheetImageView.setOnClickListener {
            val categoryList = listOf<CategoryEntity>()
            val bottomSheetDialog = CreateOrUpdateExpenseBottomSheet(

                categoryList,
                onCreateClicked = { //*expense ->*//*
                    // Lógica para criar uma nova despesa
                },
                onUpdateClicked = { //*expense ->*//*
                    // Lógica para atualizar uma despesa existente
                },
                onDeleteClicked = { //*expense ->*//*
                    // Lógica para excluir uma despesa existente
                }
            )
            bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
        }

        return view
    }
}
