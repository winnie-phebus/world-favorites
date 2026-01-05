package com.example.worldfavorites.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.worldbank.org/v2/"

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    coerceInputValues = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface WorldFavoritesApiService {

    @GET("countries?format=json")
    suspend fun getWorldBank(@Query("page") page: Int): ResponseBody
}

object WorldFavoritesApi {

    val retrofitService: WorldFavoritesApiService by lazy {
        retrofit.create(WorldFavoritesApiService::class.java)
    }

    suspend fun getCountries(): WorldBankCountriesResponse {
        val allCountries = mutableListOf<Country>()
        var paginationMetadata: PaginationMetadata? = null

        // Fetch all 6 pages
        for (page in 1..6) {
            val responseBody = retrofitService.getWorldBank(page)
            val responseString = responseBody.string()
            val response = WorldBankResponseParser.parseCountriesResponse(responseString)

            // Store pagination metadata from first page
            if (paginationMetadata == null) {
                paginationMetadata = response.pagination
            }

            // Add countries from this page
            allCountries.addAll(response.countries)
        }

        // Return combined response with all countries
        return WorldBankCountriesResponse(
            pagination = paginationMetadata ?: PaginationMetadata(1, 6, "50", 0),
            countries = allCountries
        )
    }
}

object WorldBankResponseParser {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    fun parseCountriesResponse(jsonString: String): WorldBankCountriesResponse {
        // Parse as JsonArray
        val jsonArray = json.parseToJsonElement(jsonString) as JsonArray

        // First element is pagination metadata
        val pagination = json.decodeFromJsonElement<PaginationMetadata>(jsonArray[0])

        // Second element is the array of countries
        val allEntries = json.decodeFromJsonElement<List<Country>>(jsonArray[1])

        // Filter to only include actual countries with valid capital cities
        val countries = allEntries.filter { entry ->
            entry.capitalCity.isNotBlank() &&
                    entry.longitude.isNotBlank() &&
                    entry.latitude.isNotBlank() &&
                    entry.region.id != "NA" // Exclude aggregates/regions
        }

        return WorldBankCountriesResponse(pagination, countries)
    }
}