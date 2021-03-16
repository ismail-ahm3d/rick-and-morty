package com.sam.domain.model

data class Character(
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