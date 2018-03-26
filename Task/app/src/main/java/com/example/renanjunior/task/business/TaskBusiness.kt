package com.example.renanjunior.task.business

import android.content.Context
import com.example.renanjunior.task.entities.Task
import com.example.renanjunior.task.repository.TaskRepository

/**
 * Created by renanjunior on 26/03/18.
 */
class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int): MutableList<Task> = mTaskRepository.getList(userId)

    fun delete(userId: Int) = mTaskRepository.delete(userId)

    fun insert(task: Task) = mTaskRepository.insert(task)

    fun update(task: Task) = mTaskRepository.update(task)

    fun get(id: Int) = mTaskRepository.get(id)

}