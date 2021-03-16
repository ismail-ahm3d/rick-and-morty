package com.sam.domain.model

import javax.inject.Inject

data class Character @Inject constructor(
    val episode: List<Episode>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)