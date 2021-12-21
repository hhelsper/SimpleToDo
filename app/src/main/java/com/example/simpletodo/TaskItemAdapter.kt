package com.example.simpletodo

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessControlContext

class TaskItemAdapter( val listOfItems: MutableList<String>,
                      val longClickListener: OnLongClickListener,
                        val shortClickListener: OnShortClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

        interface  OnLongClickListener{
            fun onItemLongClicked(position: Int)

        }

        interface OnShortClickListener{
            fun onItemShortClicked(position: Int)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_item_layout, parent, false)
        
        return ViewHolder(view)
    }

    fun deleteItem(i : Int){

        listOfItems.removeAt(i)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listOfItems.get(position)

        holder.textView.text = item
        if(holder.checkbox.isChecked) {
            holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else{
            holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements in our layout view
        val textView : TextView
        lateinit var checkbox : CheckBox

        init{
            textView = itemView.findViewById(R.id.item_textView)
            checkbox = itemView.findViewById(R.id.checkbox)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener{
                shortClickListener.onItemShortClicked(adapterPosition)
                true
            }

        }
    }
}