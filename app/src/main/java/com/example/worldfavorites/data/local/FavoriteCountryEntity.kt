package com.example.worldfavorites.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_countries")
data class FavoriteCountryEntity(
    @PrimaryKey
    val name: String,
    val description: String? = null,
    val capital: String? = null,
    val region: String? = null,
    val isoCode: String? = null
)
