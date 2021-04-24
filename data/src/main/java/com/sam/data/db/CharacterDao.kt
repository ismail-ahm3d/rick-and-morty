package com.sam.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam.domain.db.DatabaseCharacter

@Dao
interface CharacterDao {

    @Query("SELECT * FROM db_character")
    suspend fun getCharacters(): List<DatabaseCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg character: DatabaseCharacter)

    @Query("DELETE FROM db_character")
    fun deleteTable()
}