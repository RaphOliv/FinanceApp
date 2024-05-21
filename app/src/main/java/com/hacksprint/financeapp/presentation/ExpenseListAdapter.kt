package com.hacksprint.financeapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financeapp.R

class ExpenseListAdapter :
    ListAdapter<ExpenseUiData, ExpenseListAdapter.ExpenseViewHolder>(diffCallback()) {

    private lateinit var callBack: (ExpenseUiData) -> Unit
    fun setOnClickListener(onClick: (ExpenseUiData) -> Unit) {
        callBack = onClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_recycler_list, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, callBack)

    }

    class ExpenseViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val ivCategory = view.findViewById<ImageView>(R.id.iv_category)
        private val tvCategoryName = view.findViewById<TextView>(R.id.tv_category_name)
        private val tvExpenseName = view.findViewById<TextView>(R.id.tv_expense_name)
        private val tv_value_amount = view.findViewById<TextView>(R.id.tv_value_amount)
        private val tv_value_date = view.findViewById<TextView>(R.id.tv_value_date)
        private val iv_status = view.findViewById<ImageView>(R.id.iv_status)
        fun bind(expense: ExpenseUiData, callBack: (ExpenseUiData) -> Unit) {
           /* ivCategory.setImageResource(expense.icon)*/
            tvCategoryName.text = expense.category
            tvExpenseName.text = expense.description
            tv_value_amount.text = expense.amount.toString()
            tv_value_date.text = expense.date
            /*iv_status.setImageResource(expense.status)*/

            view.setOnClickListener {
                callBack.invoke(expense)
            }
        }
    }

    class diffCallback  : DiffUtil.ItemCallback<ExpenseUiData>() {
        override fun areItemsTheSame(oldItem: ExpenseUiData, newItem: ExpenseUiData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExpenseUiData, newItem: ExpenseUiData): Boolean {
            return oldItem.description == newItem.description
                    && oldItem.amount == newItem.amount
                    && oldItem.date == newItem.date
                    && oldItem.category == newItem.category

        }
    }
}
