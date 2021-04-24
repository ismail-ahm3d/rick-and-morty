package com.sam.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sam.domain.db.DatabaseCharacter

//@Database(entities = [DatabaseCharacter::class], version = 1)
//abstract class CharacterDatabase : RoomDatabase() {
//    abstract fun characterDao(): CharacterDao
//
//    companion object {
//        const val DATABASE_NAME = "character_db"
//    }
//}
@Database(entities = [DatabaseCharacter::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "char_db"
    }
}