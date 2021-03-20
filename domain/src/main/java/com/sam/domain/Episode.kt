package com.sam.domain

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class Episode @Inject constructor(
    @SerializedName("air_date")
    val air_date: String,
    @SerializedName("episode")
    val episodeName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)