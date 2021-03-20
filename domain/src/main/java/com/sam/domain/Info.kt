package com.sam.domain

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class Info @Inject constructor(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: Any
)