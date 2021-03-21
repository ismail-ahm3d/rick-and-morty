package com.sam.domain

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ApiResponse @Inject constructor(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    var results: List<Character>
)