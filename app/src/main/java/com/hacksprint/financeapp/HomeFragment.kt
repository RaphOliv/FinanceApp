package com.hacksprint.financeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.hacksprint.financeapp.data.CategoryEntity

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //Botao azul da
        val bottomSheetImageView = view.findViewById<ImageView>(R.id.btn_show_dielog)
        bottomSheetImageView.setOnClickListener {
            val categoryList = listOf<CategoryEntity>()
            val bottomSheetDialog = CreateOrUpdateExpenseBottomSheet(
                categoryList,
                onCreateClicked = { expense ->
                    // Lógica para criar uma nova despesa
                },
                onUpdateClicked = { expense ->
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
