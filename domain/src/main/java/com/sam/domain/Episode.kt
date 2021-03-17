package com.sam.domain

import javax.inject.Inject

data class Episode @Inject constructor(
    val air_date: String,
    val episode: String,
    val id: Int,
    val name: String,
)