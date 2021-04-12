package com.stefanini.chucknorrisio.model

import android.widget.TextView
import com.stefanini.chucknorrisio.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class CategoryItem(categoryName: String, bgColor: Int) : Item<ViewHolder>() {

     val categoryName: String
     val bgColor: Int

    init {
        this.categoryName = categoryName
        this.bgColor = bgColor
    }


    override fun getLayout(): Int {
        return R.layout.card_category
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val txtCategory = viewHolder.itemView.findViewById<TextView>(R.id.txt_category)
        txtCategory.text = categoryName
        viewHolder.itemView.setBackgroundColor(bgColor)
    }
}