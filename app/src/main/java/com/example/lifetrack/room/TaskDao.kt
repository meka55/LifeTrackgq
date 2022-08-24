package com.example.lifetrack.room
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    fun insert(taskModel: TaskModel)

    @Query("SELECT * FROM task_table")
    fun getAll():LiveData<List<TaskModel>>

    @Update
    fun update(taskModel: TaskModel)

    @Delete
    fun deleteData (taskModel: TaskModel)
}

