// ListFragment.kt
package com.hacksprint.financeapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financeapp.Adapters.CategoryListAdapter
import com.hacksprint.financeapp.Adapters.CategoryUiData
import com.hacksprint.financeapp.CreateCategoryBottomSheet
import com.hacksprint.financeapp.R
import java.util.UUID

class ListFragment : Fragment() {

    private lateinit var rvCategory: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategory = view.findViewById(R.id.rv_categories)

        val openBottomSheetButton = view.findViewById<ImageView>(R.id.btn_add_category)

        val categoryListAdapter = CategoryListAdapter()

        rvCategory.adapter = categoryListAdapter

        openBottomSheetButton.setOnClickListener {
            val bottomSheetFragment = CreateCategoryBottomSheet { categoryName ->
                // Gera um ID único para a nova categoria
                val categoryId = UUID.randomUUID().toString()
                // Adiciona a nova categoria à lista de categorias
                val updatedCategoryList = categoryListAdapter.currentList.toMutableList().apply {
                    add(CategoryUiData(categoryId, categoryName,false))
                }

                // Atualiza o adaptador com a nova lista de categorias
                categoryListAdapter.submitList(updatedCategoryList)
            }
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }
}
