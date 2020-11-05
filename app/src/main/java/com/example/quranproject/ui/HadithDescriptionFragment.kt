package com.example.quranproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.quranproject.HadithDescriptionFragmentArgs
import com.example.quranproject.R
import com.example.quranproject.adapters.AyahAdapter
import kotlinx.android.synthetic.main.fragment_hadith_description.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class HadithDescriptionFragment : Fragment() {
    var fileName =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hadith_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val name = HadithDescriptionFragmentArgs.fromBundle(requireArguments()).hadithName
       val number = HadithDescriptionFragmentArgs.fromBundle(requireArguments()).hadithNumber
        fileName = "h"+(number+1)+".txt"
        hadithName_TV.text = name
        val arrayList =ConverFileToArray(fileName)
        val adapter = AyahAdapter(view.context, arrayList)
        hadithDescriptionRecyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        hadithDescriptionRecyclerView.adapter = adapter

    }
    fun ConverFileToArray(fileName:String):ArrayList<String>{

        var arrayList: ArrayList<String> = ArrayList()
        val reader: BufferedReader
        try {
            val file : InputStream = resources.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(file))

            var line= reader.readLine()
            while (line!= null) {
                //process line

                arrayList.add(line)
                line = reader.readLine()
            }
            reader.close()

        }catch (e: IOException){
            e.printStackTrace()
        }
        return arrayList
    }

}