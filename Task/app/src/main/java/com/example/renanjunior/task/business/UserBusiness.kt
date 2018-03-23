package com.example.renanjunior.task.business

import android.content.Context
import com.example.renanjunior.task.R
import com.example.renanjunior.task.constants.TaskConstants
import com.example.renanjunior.task.entities.User
import com.example.renanjunior.task.repository.UserRepository
import com.example.renanjunior.task.util.SecurityPreferences
import com.example.renanjunior.task.util.ValidationException

/**
 * Created by renanjunior on 22/03/18.
 */
class UserBusiness(val context: Context) {

    private val mUserRepository = UserRepository.getInstance(context)

    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun insert(name: String, email: String, password: String) {
        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_campo))
            }
            if (mUserRepository.isEmailExistent(email)) {
                throw ValidationException(context.getString(R.string.email_em_uso));
            }
            val id = mUserRepository.insert(name, email, password)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun login(email: String, password: String) : Boolean{

        val user : User? = mUserRepository.get(email, password)
        return if(user!=null){
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)
             true
        }else {
             false
        }
    }

    fun isLogged() : Boolean{
        try{
            val email : String = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
            if(email !=null && email!=""){
                return true
            }
        }catch (e : Exception){
            return false
        }
        return false
    }

    fun logout() : Boolean{
        try{
            val list = arrayListOf<String>(TaskConstants.KEY.USER_EMAIL,
                                            TaskConstants.KEY.USER_ID,
                                            TaskConstants.KEY.USER_NAME)
            for (item in list){
                mSecurityPreferences.removeStoredString(item)
            }
            return true
        }catch (e:Exception){
            throw e
        }
        return false
    }

}