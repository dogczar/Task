package com.example.renanjunior.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.renanjunior.task.constants.DataBaseConstants

/**
 * Created by renanjunior on 22/03/18.
 */
class TaskDataBaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ){


    companion object {
        private val DATABASE_VERSION : Int = 1
        private val DATABASE_NAME  : String ="task.db"
    }

    private val createTableUser = """ CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME} (
                                        ${DataBaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                                        ${DataBaseConstants.USER.COLUMNS.NAME} TEXT,
                                        ${DataBaseConstants.USER.COLUMNS.EMAIL} TEXT,
                                        ${DataBaseConstants.USER.COLUMNS.PASSWORD} TEXT
                                        );"""

    private val deleteTableUser = "DROP TABLE IS EXISTS ${DataBaseConstants.USER.TABLE_NAME};"


    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(createTableUser)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(deleteTableUser)

        db.execSQL(createTableUser)

    }





}