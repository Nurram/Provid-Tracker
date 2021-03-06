package com.faris.providtracker.view.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faris.providtracker.R
import com.faris.providtracker.databinding.HabitItemListBinding
import com.faris.providtracker.view.local.entities.Habit

class HomeAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    private var habits = listOf<Habit>()
    private var onLongClick: OnLongClickListener? = null

    inner class HomeHolder(private val binding: HabitItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.apply {
                if (habit.isDone)
                    binding.ivHabitCheck.setImageResource(R.drawable.ic_baseline_check_circle_24)
                else binding.ivHabitCheck.setImageResource(R.drawable.ic_baseline_check_circle_white_24)

                tvHabitName.text = habit.name

                itemView.setOnClickListener {
                    habit.isDone = true
                    binding.ivHabitCheck.setImageResource(R.drawable.ic_baseline_check_circle_24)
                    onClick(habit)
                }

                itemView.setOnLongClickListener {
                    onLongClick?.onLongClick(habit)
                    true
                }
            }
        }
    }

    interface OnLongClickListener {
        fun onLongClick(habit: Habit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeHolder(HabitItemListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) =
        holder.bind(habits[position])

    override fun getItemCount(): Int = habits.size

    fun setData(habits: List<Habit>) {
        this.habits = habits
        notifyDataSetChanged()
    }

    fun setOnLongClickListener(onLongClick: OnLongClickListener) {
        this.onLongClick = onLongClick
    }
}