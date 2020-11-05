package com.example.quranproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {



    companion object{

        private var retrofit:Retrofit? = null

        private fun getInstance():Retrofit{
            if (retrofit==null){

                retrofit = Retrofit.Builder()
                    .baseUrl("http://api.mp3quran.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun getAPIs():WebServices{
            return getInstance().create(WebServices::class.java)
        }


    }
}