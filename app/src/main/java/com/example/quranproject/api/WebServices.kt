package com.example.quranproject.api

import com.example.quranproject.api.model.RadiosItem
import com.example.quranproject.api.model.RadiosResponse
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("radios/radio_arabic.json")
    fun getRadioChannels():Call<RadiosResponse>
}