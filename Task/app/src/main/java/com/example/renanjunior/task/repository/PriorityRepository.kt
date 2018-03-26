package com.example.renanjunior.task.repository

import android.content.Context
import android.database.Cursor
import com.example.renanjunior.task.constants.DataBaseConstants
import com.example.renanjunior.task.entities.Priority

/**
 * Created by renanjunior on 26/03/18.
 */
class PriorityRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): PriorityRepository {
            if (INSTANCE == null) {
                INSTANCE = PriorityRepository(context)
            }
            return INSTANCE as PriorityRepository
        }

        private var INSTANCE: PriorityRepository? = null
    }

    fun getList(): MutableList<Priority> {
        val list = mutableListOf<Priority>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.PRIORITY.TABLE_NAME}", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION))

                    list.add(Priority(id, description))
                }
            }
        } catch (e: Exception) {
            return list
        }
        return list
    }
}