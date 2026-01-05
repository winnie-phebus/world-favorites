package com.example.worldfavorites.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WorldFavoritesDatabase : RoomDatabase() {
    abstract fun favoriteCountryDao(): FavoriteCountryDao
}
