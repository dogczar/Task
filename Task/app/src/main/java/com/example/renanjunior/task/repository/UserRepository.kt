package com.example.renanjunior.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.renanjunior.task.constants.DataBaseConstants
import com.example.renanjunior.task.entities.User

/**
 * Created by renanjunior on 22/03/18.
 */
class UserRepository private constructor(context: Context){

    private var mTaskDataBaseHelper : TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context) : UserRepository{
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }
        private var INSTANCE: UserRepository? = null
    }


    fun insert(name : String, email : String, password : String) : Int{

        val db = mTaskDataBaseHelper.writableDatabase
        val insertValues = ContentValues()

        insertValues.put(DataBaseConstants.USER.COLUMNS.NAME, name)
        insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, email)
        insertValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, password)

        return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues).toInt()
    }

    fun isEmailExistent(email:String): Boolean{

        val ret : Boolean

        try {
            val db = mTaskDataBaseHelper.readableDatabase
            val cursor : Cursor
            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID)
            val selection =  "${DataBaseConstants.USER.COLUMNS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null )
            ret  =  cursor.count > 0
            cursor.close()

        }catch (e:Exception){
            throw e
        }
        return ret

    }

    fun get (email: String, password: String) : User?{

        var user : User? = null

        try{

            val db = mTaskDataBaseHelper.readableDatabase
            val cursor : Cursor
            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID,
                                        DataBaseConstants.USER.COLUMNS.NAME,
                                        DataBaseConstants.USER.COLUMNS.EMAIL,
                                        DataBaseConstants.USER.COLUMNS.PASSWORD)

            val selection = " ${DataBaseConstants.USER.COLUMNS.EMAIL} = ? AND  ${DataBaseConstants.USER.COLUMNS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null,null, null)

            if(cursor.count > 0){
                cursor.moveToFirst()
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID))
                val userName = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME))
                val userEmail = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL))
                val userPassword = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.PASSWORD))

                user = User(userId, userName, userEmail, userPassword)
            }

            cursor.close()

        }catch (e: Exception){
            return user
        }
        return user
    }


}