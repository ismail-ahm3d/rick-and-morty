package com.sam.domain

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class Location @Inject constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)