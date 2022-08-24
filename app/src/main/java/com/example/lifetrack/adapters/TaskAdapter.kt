package com.example.lifetrack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetrack.ItemListener
import com.example.lifetrack.room.TaskModel
import com.example.lifetrack.databinding.ItemTaskBinding

class TaskAdapter(private val list: List<TaskModel>,private val  listener:ItemListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
   inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(taskModel: TaskModel){
            binding.task.text = taskModel.task
            binding.date.text = taskModel.date
            binding.regular.text = taskModel.regular
            itemView.setOnClickListener {
                listener.itemUpDate(taskModel)
            }
        }
    }
}