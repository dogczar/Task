package com.example.renanjunior.task.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by renanjunior on 22/03/18.
 */
class SecurityPreferences (context: Context) {


    val mSharedPreferences : SharedPreferences = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)


    fun storeString(key : String, value: String){
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString(key : String) : String{
        return mSharedPreferences.getString(key, "")
    }

    fun removeStoredString(key: String){
        mSharedPreferences.edit().remove(key).apply()
    }

}