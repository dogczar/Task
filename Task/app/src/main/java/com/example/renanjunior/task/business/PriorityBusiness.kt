package com.example.renanjunior.task.business

import android.content.Context
import com.example.renanjunior.task.entities.Priority
import com.example.renanjunior.task.repository.PriorityRepository

/**
 * Created by renanjunior on 26/03/18.
 */
class PriorityBusiness(context: Context){


    private val mPriorityRepository : PriorityRepository = PriorityRepository.getInstance(context)

    fun getList() : MutableList<Priority> = mPriorityRepository.getList()

}

