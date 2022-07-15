package com.example.task

import com.google.gson.annotations.SerializedName

data class ResponseTamil(

	@field:SerializedName("user_data")
	val userData: List<UserDataItem?>? = null
)

data class UserDataItem(

	@field:SerializedName("is_clickable")
	val isClickable: Boolean? = null,

	@field:SerializedName("discription")
	val discription: String? = null,

	@field:SerializedName("post_url")
	val postUrl: String? = null,

	@field:SerializedName("post_type")
	val postType: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
