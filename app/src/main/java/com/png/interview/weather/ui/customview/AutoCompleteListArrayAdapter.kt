package com.png.interview.weather.ui.customview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.png.interview.R

class AutoCompleteListArrayAdapter(
    private val context: Context,
    private var suggestions: List<String> = emptyList())
    : BaseAdapter() {

    override fun getCount(): Int = suggestions.size
    override fun getItem(position: Int) = suggestions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val suggestion = getItem(position)

        val (inflatedConvertView, viewHolder) =
            if(convertView != null) {
                convertView to convertView.tag as MyViewHolder
            } else {
                val inflatedConvertView = LayoutInflater.from(context)
                    .inflate(R.layout.view_autocomplete_suggestion_item, null)
                val suggestionTv: TextView = inflatedConvertView.findViewById(R.id.tv_autocomplete_suggestion2)
                val viewHolder = MyViewHolder(suggestionTv)
                inflatedConvertView.tag = viewHolder
                inflatedConvertView to viewHolder
            }

        viewHolder.suggestionTv?.text = suggestion
        return inflatedConvertView
    }

    fun updateData(updateSuggestions: List<String>) {
        suggestions = updateSuggestions
        notifyDataSetChanged()
    }

    private class MyViewHolder(
        val suggestionTv: TextView?
    )
}