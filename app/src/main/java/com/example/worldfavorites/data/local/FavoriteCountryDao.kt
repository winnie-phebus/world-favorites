package com.example.worldfavorites.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCountryDao {
    @Query("SELECT * FROM favorite_countries ORDER BY name ASC")
    fun getAllFavoriteCountries(): Flow<List<FavoriteCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCountry(country: FavoriteCountryEntity)

    @Query("DELETE FROM favorite_countries WHERE name = :countryName")
    suspend fun deleteFavoriteCountry(countryName: String)
}
