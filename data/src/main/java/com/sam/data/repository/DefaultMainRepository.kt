package com.sam.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sam.data.db.CharacterDao
import com.sam.data.paging.DataPagingSource
import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.Constants.BASE_URL
import com.sam.data.util.Constants.HTTP_ERROR_TEXT
import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import com.sam.domain.asDatabaseModel
import com.sam.domain.asDomainModel
import com.sam.domain.db.asDomainModel
import com.sam.domain.network.NetworkCharacter
import com.sam.domain.network.asDatabaseCharacter
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

class DefaultMainRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao: CharacterDao,
    private val randomPage: Int
) : MainRepository {

    override fun getAllCharacters(): Flow<PagingData<NetworkCharacter>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DataPagingSource(api) }
        ).flow
    }

    override suspend fun getRandomCharacters(): Resource<List<Character>> {
        return try {

            val characters: List<Character>

            val apiResponse = api.getCharacters(randomPage)
            val networkCharacter = apiResponse.results

            dao.deleteTable()

            val databaseCharacters = networkCharacter.asDatabaseCharacter()

            databaseCharacters.map { databaseCharacter ->
                dao.insertAll(databaseCharacter)
            }

            characters = databaseCharacters.map {
                it.asDomainModel()
            }

            Resource.Success(characters)

        } catch (e: HttpException) {
            Resource.Error(e.message ?: "Something went wrong..!!")

        } catch (e: IOException) {

            val currentDatabaseCharacters = dao.getCharacters()

            return if (
                currentDatabaseCharacters.isNotEmpty()
                && e.message!!.contains(HTTP_ERROR_TEXT)
            ) {
                val characters = currentDatabaseCharacters.map {
                    it.asDomainModel()
                }
                Resource.Success(characters)
            } else {
                Resource.Error(e.message ?: "Something went wrong..!!")
            }
        }
    }

    override suspend fun getCharacterById(id: Int): Resource<NetworkCharacter> {
        return try {
            val character = api.getCharacterById(id)
            Resource.Success(character)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong..!!")
        }
    }
}