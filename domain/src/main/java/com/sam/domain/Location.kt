package com.sam.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class Location @Inject constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
): Parcelable