package com.sam.domain

import javax.inject.Inject

data class Character @Inject constructor(
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
//    val episode: List<Episode>,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)