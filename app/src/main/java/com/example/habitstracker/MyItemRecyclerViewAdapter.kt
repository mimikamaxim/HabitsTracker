package com.example.habitstracker

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
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
        holder.itemTitle.text = item.name
        holder.itemDescriptions.text = item.description
        holder.itemPriority.text = "Приоритет: ${item.priority.toString()}"
        holder.itemAmountDone.text = "Выполнено: ${item.amountDone}"
        holder.itemPeriod.text = "Периодичность: ${item.period.toString()}"
        holder.itemColorAndIsGood.setColorFilter(item.color)
        if (item.isGood)
            holder.itemColorAndIsGood.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        else
            holder.itemColorAndIsGood.setImageResource(R.drawable.ic_baseline_thumb_down_24)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHabitsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.itemTitle
        val itemDescriptions: TextView = binding.itemDescription
        val itemPriority: TextView = binding.itemPriority
        val itemAmountDone: TextView = binding.itemAmountDone
        val itemPeriod: TextView = binding.itemPeriod
        val itemColorAndIsGood: ImageView = binding.itemColorAndIsGood

        override fun toString(): String {
            return super.toString() + " '" + itemDescriptions.text + "'"
        }
    }
}