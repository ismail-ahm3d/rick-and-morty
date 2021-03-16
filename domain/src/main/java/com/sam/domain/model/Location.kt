package com.sam.domain.model

import javax.inject.Inject

data class Location @Inject constructor(
    val id: Int,
    val name: String
)