package com.example.quranproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sebha.*


class SebhaFragment : Fragment() {

    var praise :Int = 0
    var allPraise :Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sebha, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ArrayAdapter.createFromResource(
            view.context, R.array.spinnerArray, R.layout.support_simple_spinner_dropdown_item
        )
            .also { adapter -> spinner.adapter = adapter }

        // when sebha icon clicked increase the two counters
        sebha_icon.setOnClickListener{
            praise++
            allPraise++
            displayAllPraiseCounter()
            displayPraiseCounter()
        }

        // when reset icon clicked resets all counters
        reset_icon.setOnClickListener{
            praise = 0
            allPraise =0
            displayPraiseCounter()
            displayAllPraiseCounter()
        }
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                // when any new spinner item reset only its counter
                praise =0
                displayPraiseCounter()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }


    // display spinner item praise counter times
    fun displayPraiseCounter(){
        praiseCounter.text = ""+praise
    }

//    display the all praises counter
    fun displayAllPraiseCounter(){
        allPraiseCounter.text=""+allPraise
    }


}