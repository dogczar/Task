package com.example.renanjunior.task.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.example.renanjunior.task.R
import com.example.renanjunior.task.business.PriorityBusiness
import kotlinx.android.synthetic.main.activity_task_form.*
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var  mPriorityBusiness : PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)


        setListiners()

        loadPriorities()
    }

    override fun onClick(view: View) {

        when (view.id){

            R.id.buttonDate -> {
                openDatePickerDialog()
            }
            R.id.buttonAdicionar -> {
                handleAddTask()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }


    private fun openDatePickerDialog(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOfMonth).show()

    }

    private fun handleAddTask(){

    }

    private fun setListiners() {
        buttonDate.setOnClickListener(this)
        buttonAdicionar.setOnClickListener(this)
    }

    private fun loadPriorities() {

        val lstPriorities = mPriorityBusiness.getList()

        val lst = lstPriorities.map { it.description }


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lst)

        spinnerPriority.adapter = adapter

    }
}
