package com.example.quranproject.api.model

import com.google.gson.annotations.SerializedName

data class RadiosResponse(

	@field:SerializedName("radios")
	val radios: ArrayList<RadiosItem?>? = null
)

data class RadiosItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("radio_url")
	val radioUrl: String? = null
)
