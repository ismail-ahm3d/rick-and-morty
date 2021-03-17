package com.sam.domain

import javax.inject.Inject

data class Location @Inject constructor(
    val id: Int,
    val name: String
)