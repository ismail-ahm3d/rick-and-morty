package com.sam.data.repository

import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
import com.sam.domain.Character

interface MainRepository {

    suspend fun getAllCharacters(): Resource<ApiResponse>
    suspend fun getCharacterById(id: Int): Resource<Character>
}