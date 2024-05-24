package com.hacksprint.financeapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financeapp.R

class ListIconsAdapter(private val icons:List<Int>): RecyclerView.Adapter<ListIconsAdapter.IconViewholder>(){
    private var selectPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_selection_style,parent,false)
        return IconViewholder(view)
    }

    override fun onBindViewHolder(holder: IconViewholder, position: Int) {
        holder.icon_list.setImageResource(icons[position])

        holder.circle_select.visibility = if(position == selectPosition)
            View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener{
            val previewSlect = selectPosition
            selectPosition = position
            notifyItemChanged(previewSlect)
            notifyItemChanged(selectPosition)
        }
    }

    override fun getItemCount(): Int = icons.size


    inner class IconViewholder(itemView: View):
        RecyclerView.ViewHolder(itemView){
        val icon_list: ImageView = itemView.findViewById(R.id.icon_list)
        val circle_select: View = itemView.findViewById(R.id.circle_select)

    }

}
