package com.sam.data.repository

import androidx.paging.PagingData
import com.sam.data.util.Resource
import com.sam.domain.Character
import com.sam.domain.Episode
import com.sam.domain.network.NetworkCharacter
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllCharacters(): Flow<PagingData<NetworkCharacter>>
    suspend fun getRandomCharacters(): Resource<List<Character>>
    suspend fun getCharacterById(id: Int): Resource<NetworkCharacter>
    suspend fun getEpisodeById(id: Int): Resource<Episode>
}