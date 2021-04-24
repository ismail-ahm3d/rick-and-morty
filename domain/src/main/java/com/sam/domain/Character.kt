package com.sam.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sam.domain.db.DatabaseCharacter
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class Character @Inject constructor(
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location?,
    val name: String,
    val episodes: List<String>?,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
) : Parcelable

fun Character.asDatabaseModel(): DatabaseCharacter {
    return let { character ->
        DatabaseCharacter(
            name = character.name,
            gender = character.gender,
            id = character.id,
            image = character.image,
            species = character.species,
            status = character.status,
            type = character.type,
            url = character.url,
        )
    }
}