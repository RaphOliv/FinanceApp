package com.hacksprint.financeapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financeapp.R
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseListAdapter(param: (Any) -> Unit)/*(private val callBack: (ExpenseUiData) -> Unit)*/ :
    ListAdapter<ExpenseUiData, ExpenseListAdapter.ExpenseViewHolder>(diffCallback()) {

    private lateinit var callBack: (ExpenseUiData) -> Unit
    fun setOnClickListener(onClick: (ExpenseUiData) -> Unit) {
        callBack = onClick
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_recycler_list, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = getItem(position)
        holder.bind(expense, callBack)
    }


    class ExpenseViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val ivCategory: ImageView = view.findViewById(R.id.iv_category)
        private val tvCategoryName: TextView = view.findViewById(R.id.tv_category_name)
        private val tvExpenseName: TextView = view.findViewById(R.id.tv_expense_name)
        private val tvValueAmount: TextView = view.findViewById(R.id.tv_value_amount)
        private val tvValueDate: TextView = view.findViewById(R.id.tv_value_date)
        private val ivStatus: ImageView = view.findViewById(R.id.iv_status)

        fun bind(expense: ExpenseUiData, callBack: (ExpenseUiData) -> Unit) {
            /* ivCategory.setImageResource(expense.icon)*/
            tvCategoryName.text = expense.category
            tvExpenseName.text = expense.description
            tvValueAmount.text = expense.amount.toString()
            tvValueDate.text = formatDate(expense.date)
            /*ivStatus.setImageResource(expense.status)*/

            view.setOnClickListener {
                callBack.invoke(expense)
            }
        }

        private fun formatDate(date: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return formatter.format(parser.parse(date)!!)
        }
    }


    class diffCallback : DiffUtil.ItemCallback<ExpenseUiData>() {
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
