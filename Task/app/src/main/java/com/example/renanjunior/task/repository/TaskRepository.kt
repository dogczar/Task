package com.example.renanjunior.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.renanjunior.task.constants.DataBaseConstants
import com.example.renanjunior.task.entities.Task

/**
 * Created by renanjunior on 26/03/18.
 */
class TaskRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null
    }

    fun insert(task: Task) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase
            val insertValues = ContentValues()

            val complete: Int = if (task.complete) 1 else 0

            insertValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)

            db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValues).toInt()
        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(id: Int) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val selection = " ${DataBaseConstants.TASK.COLUMNS.ID} = ? "
            val selectionArgs = arrayOf(id.toString())

            db.delete(DataBaseConstants.TASK.TABLE_NAME, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }


    fun update(task: Task) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase
            val updateValues = ContentValues()
            val complete: Int = if (task.complete) 1 else 0

            updateValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)

            val selection = " ${DataBaseConstants.TASK.COLUMNS.ID} = ? "
            val selectionArgs = arrayOf(task.id.toString())

            db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }


    fun get(id: Int): Task? {

        var task: Task? = null

        try {

            val db = mTaskDataBaseHelper.readableDatabase
            val cursor: Cursor
            val projection = arrayOf(DataBaseConstants.TASK.COLUMNS.ID,
                    DataBaseConstants.TASK.COLUMNS.USERID,
                    DataBaseConstants.TASK.COLUMNS.PRIORITYID,
                    DataBaseConstants.TASK.COLUMNS.COMPLETE,
                    DataBaseConstants.TASK.COLUMNS.DESCRIPTION,
                    DataBaseConstants.TASK.COLUMNS.DUEDATE)

            val selection = " ${DataBaseConstants.TASK.COLUMNS.ID} = ? "
            val selectionArgs = arrayOf(id.toString())

            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                val complete = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1

                task = Task(id, userId, priorityId, description, complete, dueDate)
            }

            cursor.close()

        } catch (e: Exception) {
            return task
        }
        return task
    }


    fun getList(userId: Int): MutableList<Task> {
        val list = mutableListOf<Task>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME} WHERE ${DataBaseConstants.TASK.COLUMNS.USERID} = $userId", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USERID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                    val complete = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1
                    val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    list.add(Task(id, userId, priorityId, description, complete, dueDate))
                }
            }
        } catch (e: Exception) {
            return list
        }
        return list
    }


}