package com.example.worldfavorites.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Root response wrapper
@Serializable
data class WorldBankCountriesResponse(
    val pagination: PaginationMetadata,
    val countries: List<Country>
)

@Serializable
data class PaginationMetadata(
    val page: Int,
    val pages: Int,
    @SerialName("per_page")
    val perPage: String, // Note: API returns this as string, not int
    val total: Int
)

@Serializable
data class Country(
    val id: String,
    val iso2Code: String,
    val name: String,
    val region: Region,
    @SerialName("adminregion")
    val adminRegion: AdminRegion,
    @SerialName("incomeLevel")
    val incomeLevel: IncomeLevel,
    @SerialName("lendingType")
    val lendingType: LendingType,
    @SerialName("capitalCity")
    val capitalCity: String,
    val longitude: String,
    val latitude: String
)

@Serializable
data class Region(
    val id: String,
    @SerialName("iso2code")
    val iso2Code: String,
    val value: String
)

@Serializable
data class AdminRegion(
    val id: String,
    @SerialName("iso2code")
    val iso2Code: String,
    val value: String
)

@Serializable
data class IncomeLevel(
    val id: String,
    @SerialName("iso2code")
    val iso2Code: String,
    val value: String
)

@Serializable
data class LendingType(
    val id: String,
    @SerialName("iso2code")
    val iso2Code: String,
    val value: String
)