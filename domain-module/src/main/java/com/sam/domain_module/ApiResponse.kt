package com.sam.domain_module

import javax.inject.Inject

data class ApiResponse @Inject constructor(
    val info: Info,
    val results: List<Character>
)