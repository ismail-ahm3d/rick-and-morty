package com.sam.domain.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sam.domain.Character
import com.sam.domain.Location
import com.sam.domain.db.DatabaseCharacter
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class NetworkCharacter @Inject constructor(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("episode")
    val episodes: List<String>,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
) : Parcelable

fun NetworkCharacter.asDomainModel(): Character {
    return let { networkCharacter ->
        Character(
            name = networkCharacter.name,
            gender = networkCharacter.gender,
            id = networkCharacter.id,
            image = networkCharacter.image,
            location = networkCharacter.location,
            species = networkCharacter.species,
            episodes = networkCharacter.episodes,
            status = networkCharacter.status,
            type = networkCharacter.type,
            url = networkCharacter.url,
        )
    }
}

fun List<NetworkCharacter>.asDatabaseCharacter(): List<DatabaseCharacter> {
    return this.map { networkCharacter ->
        DatabaseCharacter(
            name = networkCharacter.name,
            gender = networkCharacter.gender,
            id = networkCharacter.id,
            image = networkCharacter.image,
            species = networkCharacter.species,
            status = networkCharacter.status,
            type = networkCharacter.type,
            url = networkCharacter.url,
        )
    }
}

