package com.example.renanjunior.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.renanjunior.task.R
import com.example.renanjunior.task.business.UserBusiness
import com.example.renanjunior.task.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var mUserBusiness : UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Variaveis
        mUserBusiness = UserBusiness(this)

        // Eventos
        setListiners()


    }

    private fun setListiners() {
        buttonSave.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun handleSave() {

       try {
           val name = editName.text.toString()
           val email = editEmail.text.toString()
           val password = editPassword.text.toString()
           mUserBusiness.insert(name, email, password)
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       }catch (e : ValidationException){
            Toast.makeText(this, e.message,Toast.LENGTH_LONG).show()
       }catch (e: Exception){
           Toast.makeText(this, getString(R.string.erro_generico), Toast.LENGTH_LONG).show()
       }
    }
}
