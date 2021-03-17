package com.sam.domain

import javax.inject.Inject

data class Info @Inject constructor(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)