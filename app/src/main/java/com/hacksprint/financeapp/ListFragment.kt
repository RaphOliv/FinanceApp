package com.hacksprint.financeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ListFragment: Fragment() {

    private var rvCategory: RecyclerView? = null
    private var rvExpenses: RecyclerView? = null
    private lateinit var openBottomSheetButton: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategory = view.findViewById<RecyclerView>(R.id.rv_categories)
        rvExpenses = view.findViewById<RecyclerView>(R.id.rv_expenses)

openBottomSheetButton.findViewById<ImageView>(R.id.btn_add_categorie)
        openBottomSheetButton.setOnClickListener {
            val bottomSheetFragment = DielogList()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
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