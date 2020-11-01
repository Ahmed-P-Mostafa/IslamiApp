package com.example.quranproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quranproject.QuranFragmentDirections.FromQuranToRead
import com.example.quranproject.adapters.QuranAdapter
import com.example.quranproject.adapters.QuranAdapter.OnItemClickListenerInterface
import kotlinx.android.synthetic.main.fragment_hadith.*

class HadithFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hadith, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var array = initialArray()

       hadithRecyclerView.layoutManager = GridLayoutManager(
           view.context,
           3,
           GridLayoutManager.HORIZONTAL,
           false
       )

        val adapter = QuranAdapter(view.context, array)

        hadithRecyclerView.adapter = adapter
        adapter.onSuraClicked(object : OnItemClickListenerInterface {
            override fun onItemClicked(pos: Int) {
                val action  = HadithFragmentDirections.FromHadithToReadHadith(pos,"حديث "+(pos+1))
                findNavController().navigate(action)
            }
        })


    }
    fun initialArray(): Array<String?> {
        val array = arrayOfNulls<String?>(50)
        for (i in 0..49) {
            array[i] = "حديث " + (i + 1)
        }
        return array
    }


}