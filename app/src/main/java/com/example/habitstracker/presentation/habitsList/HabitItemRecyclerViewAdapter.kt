package com.example.habitstracker.presentation.habitsList


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsBinding
import com.example.habitstracker.presentation.HabitItemPresentationModel

class HabitItemRecyclerViewAdapter(
    private var values: List<HabitItemPresentationModel>,
    private val clickItemHandler: ClickItemHandler,
    private val context: Context
) : RecyclerView.Adapter<HabitItemRecyclerViewAdapter.ViewHolder>() {

    fun updateList(list: List<HabitItemPresentationModel>) {
        values = list
        notifyDataSetChanged()
    }

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
        holder.itemPriority.text = buildString {
            append(context.getString(R.string.priority_text))
            append(": ")
            append(item.priority.toString())
        }
        holder.itemAmountDone.text = buildString {
            append(context.getString(R.string.done_amount_text))
            append(": ")
            append(item.amountDone)
        }
        holder.itemPeriod.text = buildString {
            append(context.getString(R.string.period_text))
            append(": ")
            append(item.period.toString())
        }
        holder.itemColorAndIsGood.setColorFilter(item.color)
        if (item.isGood)
            holder.itemColorAndIsGood.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        else
            holder.itemColorAndIsGood.setImageResource(R.drawable.ic_baseline_thumb_down_24)
        holder.rootView.setOnClickListener {
            clickItemHandler.onClickItemHandler(it, item.getID())
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHabitsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.itemTitle
        val itemDescriptions: TextView = binding.itemDescription
        val itemPriority: TextView = binding.itemPriority
        val itemAmountDone: TextView = binding.itemAmountDone
        val itemPeriod: TextView = binding.itemPeriod
        val itemColorAndIsGood: ImageView = binding.itemColorAndIsGood
        val rootView = binding.root

        override fun toString(): String {
            return super.toString() + " '" + itemDescriptions.text + "'"
        }
    }
}