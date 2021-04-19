package com.sam.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllCharacters(): Flow<PagingData<Character>>
    suspend fun getRandomCharacters(): Resource<ApiResponse>
    suspend fun getCharacterById(id: Int): Resource<Character>
}