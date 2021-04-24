package com.sam.domain.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sam.domain.Character
import com.sam.domain.Location
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Entity(tableName = "db_character")
data class DatabaseCharacter(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "url")
    val url: String,
)

fun DatabaseCharacter.asDomainModel(): Character {
    return let { databaseCharacter ->
        Character(
            name = databaseCharacter.name,
            gender = databaseCharacter.gender,
            id = databaseCharacter.id,
            image = databaseCharacter.image,
            location = null,
            species = databaseCharacter.species,
            episodes = null,
            status = databaseCharacter.status,
            type = databaseCharacter.type,
            url = databaseCharacter.url,
        )
    }
}