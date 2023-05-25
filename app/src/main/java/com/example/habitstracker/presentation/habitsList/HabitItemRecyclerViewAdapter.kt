package com.example.habitstracker.presentation.habitsList


import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entitys.DomainHabitEntity
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsBinding

class HabitItemRecyclerViewAdapter(
    private var values: List<DomainHabitEntity>,
    private val clickItemHandler: ClickItemHandler,
    private val context: Context
) : RecyclerView.Adapter<HabitItemRecyclerViewAdapter.ViewHolder>() {

    fun updateList(list: List<DomainHabitEntity>) {
        values = list
        notifyDataSetChanged()//TODO 3 optimise list updates
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
        holder.itemName.text = item.name
        holder.itemDescriptions.text = item.description
        holder.itemPriority.text = buildString {
            append(context.getString(R.string.priority_text))
            append(": ")
            append(item.priority.toString())
        }
        holder.itemTotalCompleteTimes.text = buildString {
            append(context.getString(R.string.you_complete_the_habit_text))
            append(": ")
            append(item.totalCompleteTimes.toString())
            append(" ")
            append(context.getString(R.string.times_text))
        }
        holder.itemPeriod.text = buildString {
            append(context.getString(R.string.allowed_frequency_text))
            append(": ")
            append(item.frequencyOfAllowedExecutions.toString())
            append(" ")
            append(context.getString(R.string.times_in_text))
            append(" ")
            append(item.periodInDays.toString())
            append(" ")
            append(context.getString(R.string.days_text))
        }
        holder.itemColorAndIsGood.setColorFilter(item.color)
        holder.itemCurrentCompleteTimes.text = buildString {
            append(context.getString(R.string.now_done_text))
            append(" ")
            append(item.currentCompleteTimes.toString())
            append(" ")
            append(context.getString(R.string.times_text))
        }
        holder.itemDoneDates.text = StringBuilder().apply { //TODO 3 make it more accurate
            val arr = item.doneDates
            arr.forEach {
                append(it.dayOfMonth.toString())
                append(" ")
            }
        }

        if (item.isGood)
            holder.itemColorAndIsGood.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        else
            holder.itemColorAndIsGood.setImageResource(R.drawable.ic_baseline_thumb_down_24)

        holder.rootView.setOnClickListener {
            clickItemHandler.onNavigateToDetails(id = item.getID())
        }

        holder.btnDone.setOnClickListener {
            clickItemHandler.onAddDone(id = item.getID())
        }

        holder.btnMore.setOnClickListener {
            showPopupMenu(view = it, id = item.getID())
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHabitsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName: TextView = binding.itemName
        val itemDescriptions: TextView = binding.itemDescription
        val itemPriority: TextView = binding.itemPriority
        val itemTotalCompleteTimes: TextView = binding.itemTotalCompleteTimes
        val itemPeriod: TextView = binding.itemPeriod
        val itemCurrentCompleteTimes: TextView = binding.itemCurrentCompleteTimes
        val itemColorAndIsGood: ImageView = binding.itemColorAndIsGood
        val itemDoneDates: TextView = binding.doneDates
        val btnDone: ImageView = binding.doneBtn
        val btnMore: ImageView = binding.moreBtn
        val rootView = binding.root

        override fun toString(): String {
            return super.toString() + " '" + itemDescriptions.text + "'"
        }
    }

    private fun showPopupMenu(view: View, id: Int) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context

        //TODO 3 maybe add reset executions
        popupMenu.menu.add(0, EDIT_HABIT, Menu.NONE, context.getString(R.string.edit_habit_text))
        popupMenu.menu.add(
            0,
            DELETE_HABIT,
            Menu.NONE,
            context.getString(R.string.delete_habit_text)
        )

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                EDIT_HABIT -> {
                    clickItemHandler.onNavigateToDetails(id)
                }
                DELETE_HABIT -> {
                    clickItemHandler.onDeleteItem(id)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object {
        private const val EDIT_HABIT = 1
        private const val DELETE_HABIT = 2
    }
}