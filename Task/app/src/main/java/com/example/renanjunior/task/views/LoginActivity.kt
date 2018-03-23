package com.example.renanjunior.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.renanjunior.task.R
import com.example.renanjunior.task.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var mUserBusiness : UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)

        isLogged()

        setListiners()
    }

    private fun setListiners() {
        buttonLogin.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonLogin -> {
                handleLogin()
            }
        }
    }

    private fun isLogged(){
        if (mUserBusiness.isLogged()){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if(email != "" && password!="") {
            try {
                if(mUserBusiness.login(email, password)){
                    val intent : Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, getString(R.string.usuario_nao_cadastrado), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception){
                throw e
            }

        }else{
            Toast.makeText(this, "Dados não preenchidos", Toast.LENGTH_LONG).show()
        }

    }
}
