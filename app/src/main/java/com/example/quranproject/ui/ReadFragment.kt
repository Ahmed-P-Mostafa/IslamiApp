package com.example.quranproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quranproject.R
//import com.example.quranproject.ReadFragmentArgs
import com.example.quranproject.adapters.*
import kotlinx.android.synthetic.main.fragment_read.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ReadFragment : Fragment() {
    var fileName =""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return LayoutInflater.from(context).inflate(R.layout.fragment_read, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val position :Int = ReadFragmentArgs.fromBundle(requireArguments()).suraNumber
            fileName = (position+1).toString()+".txt"

            souraName_TV.text = ReadFragmentArgs.fromBundle(requireArguments()).suraName
        var arrayList = ConverFileToArray(fileName)
        souraRecyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        val adapter = AyahAdapter(view.context,arrayList)
        souraRecyclerView.adapter = adapter


    }

    fun ConverFileToArray(fileName:String):ArrayList<String>{

       var arrayList: ArrayList<String> = ArrayList()
        val reader:BufferedReader
        try {
            val file :InputStream = resources.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(file))

            var line= reader.readLine()
            while (line!= null) {
                //process line

                arrayList.add(line)
                line = reader.readLine()
            }
            reader.close()

        }catch (e:IOException){
            e.printStackTrace()
        }
        return arrayList
    }

}