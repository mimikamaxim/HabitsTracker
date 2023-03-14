package com.example.habitstracker

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.habitstracker.data.HabitItem

import com.example.habitstracker.placeholder.PlaceholderContent.PlaceholderItem
import com.example.habitstracker.databinding.FragmentHabitsBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<HabitItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentHabitsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.name
        holder.contentView.text = item.description
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHabitsBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemTitle
        val contentView: TextView = binding.itemDescription

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}