package com.hacksprint.financeapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.hacksprint.financeapp.CreateOrUpdateExpenseBottomSheet
import com.hacksprint.financeapp.R
import com.hacksprint.financeapp.data.CategoryEntity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
        bottomSheetImageView.setOnClickListener {
            val categoryList = listOf<CategoryEntity>()
            val bottomSheetDialog = CreateOrUpdateExpenseBottomSheet(
                categoryList = categoryList,
                onCreateClicked = { expense ->
                    // Handle creation logic here
                },
                onUpdateClicked = { expense ->
                    // Handle update logic here
                }
            )
            bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
        }

        return view
    }
}
