package com.sam.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sam.data.paging.DataPagingSource
import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.Constants.BASE_URL
import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.random.Random

class DefaultMainRepository @Inject constructor(
    private val api: ServiceApi,
    private val randomPage: Int
) : MainRepository {

//    override suspend fun getAllCharacters(): Resource<ApiResponse> {
//        return try {
//            val apiResponse = api.getCharacters()
//            Resource.Success(apiResponse)
//        } catch (e: Exception) {
//            Resource.Error(e.message ?: "Something went wrong..!!")
//        }
//    }

    override fun getAllCharacters(): Flow<PagingData<Character>> {

        Log.d(TAG, "getAllCharacters: $randomPage")

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DataPagingSource(api) }
        ).flow

//        return try {
////            val apiResponse = api.getCharacters()
////            Resource.Success(apiResponse)
//
//        } catch (e: Exception) {
////            Resource.Error(e.message ?: "Something went wrong..!!")
//        }
    }

    override suspend fun getRandomCharacters(): Resource<ApiResponse> {
        return try {
            val apiResponse = api.getCharacters(randomPage)
            Log.d(TAG, "getRandomCharacters: $randomPage")
            Resource.Success(apiResponse)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong..!!")
        }
    }

    override suspend fun getCharacterById(id: Int): Resource<Character> {
        return try {
            val character = api.getCharacterById(id)
            Resource.Success(character)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong..!!")
        }
    }
}