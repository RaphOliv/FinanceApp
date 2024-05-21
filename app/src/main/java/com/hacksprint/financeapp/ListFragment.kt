package com.hacksprint.financeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financeapp.DataBase.CategoryUiData

class ListFragment : Fragment() {

    private var rvCategory: RecyclerView? = null
    private var rvExpenses: RecyclerView? = null

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

        val openBottomSheetButton = view.findViewById<ImageView>(R.id.btn_add_category)

        openBottomSheetButton.setOnClickListener {
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

        }
    }

    private fun CategoryUiData(name: String): CategoryUiData {
        return CategoryUiData(name)

    }

    override fun onResume() {
        super.onResume()

        val categoryListAdapter = CategoryListAdapter()

        categoryListAdapter.setOnClickListener { category ->

        }
        categoryListAdapter.setOnLongClickListener { category ->

            true
        }
        rvCategory?.adapter = categoryListAdapter

        val expenseListAdapter = ExpenseListAdapter()
        expenseListAdapter.setOnClickListener { expense ->

        }
        rvExpenses?.adapter = expenseListAdapter
    }


}