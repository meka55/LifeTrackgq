package com.example.lifetrack.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "task_table")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val task:String,
    val date:String,
    val regular:String,
):Serializable