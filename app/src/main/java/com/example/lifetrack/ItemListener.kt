package com.example.lifetrack

import com.example.lifetrack.room.TaskModel

interface ItemListener {
    fun itemUpDate(taskModel: TaskModel)
    fun delete (taskModel: TaskModel)
}