package com.example.quranproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.fragment)
        bottomNAv.setupWithNavController(navController)




        val fragmentTransaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment,QuranFragment())

        /*bottomNAv.setOnNavigationItemSelectedListener{

            if (it.itemId==R.id.action_quran){
                navController.navigate(R.id.QuranFragment,null)

            }
            else if (it.itemId==R.id.action_Seb7a){
                //Navigation.findNavController(this@MainActivity,R.id.fragment).navigate(R.id.sebhaFragment)
                navController.navigate(R.id.sebhaFragment,null)
                *//*fragmentTransaction.replace(R.id.fragment,HadithFragment()).commit()
                return@setOnNavigationItemSelectedListener true*//*
            }
            else if (it.itemId==R.id.action_hadith){
                    navController.navigate(R.id.hadithFragment,null)
            }

            return@setOnNavigationItemSelectedListener true
        }*/


    }



}