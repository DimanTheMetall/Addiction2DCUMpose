package com.example.addiction2dcumpose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.addiction2dcumpose.data.database.entitys.MangaEntity
import com.example.addiction2dcumpose.data.database.entitys.MangaItemEntity

@Database(
    version = 1,
    entities = [MangaEntity::class, MangaItemEntity::class]
)
abstract class AddictionDataBase : RoomDatabase() {

    abstract fun getMangaDataDao(): MangaDataDao

}