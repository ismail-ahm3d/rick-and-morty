package com.sam.domain

import com.google.gson.annotations.SerializedName
import com.sam.domain.network.NetworkCharacter
import javax.inject.Inject

data class ApiResponse @Inject constructor(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    var results: List<NetworkCharacter>
)

fun ApiResponse.asDomainModel(): List<Character> {
    return results.map { character ->
        Character(
            name = character.name,
            gender = character.gender,
            id = character.id,
            image = character.image,
            location = character.location,
            species = character.species,
            episodes = character.episodes,
            status = character.status,
            type = character.type,
            url = character.url,
        )
    }
}