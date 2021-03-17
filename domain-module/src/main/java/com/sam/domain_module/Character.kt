package com.sam.domain_module

import javax.inject.Inject

data class Character @Inject constructor(
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