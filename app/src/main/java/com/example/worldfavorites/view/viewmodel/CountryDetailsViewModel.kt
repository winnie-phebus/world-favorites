package com.example.worldfavorites.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldfavorites.data.local.FavoriteCountryDao
import com.example.worldfavorites.data.local.FavoriteCountryEntity
import com.example.worldfavorites.view.components.CountryItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val favoriteCountryDao: FavoriteCountryDao
) : ViewModel() {

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private var currentCountry: CountryItemState? = null
    private var loadJob: Job? = null

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun resetDescription() {
        _description.value = ""
    }

    fun loadExistingCountry(countryState: CountryItemState) {
        currentCountry = countryState
        // Cancel previous collection job to prevent memory leak
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            favoriteCountryDao.getAllFavoriteCountries().collect { countries ->
                val existingCountry = countries.find { it.name == countryState.name }
                if (existingCountry != null) {
                    _description.value = existingCountry.description ?: ""
                }
            }
        }
    }

    fun setCountryData(countryState: CountryItemState) {
        currentCountry = countryState
    }

    fun saveFavoriteCountry(countryName: String, onSaved: () -> Unit) {
        viewModelScope.launch {
            val favoriteCountry = FavoriteCountryEntity(
                name = countryName,
                description = _description.value.takeIf { it.isNotBlank() },
                capital = currentCountry?.capital,
                region = currentCountry?.region,
                isoCode = currentCountry?.isoCode
            )
            // insertFavoriteCountry uses REPLACE strategy, so it updates if exists
            favoriteCountryDao.insertFavoriteCountry(favoriteCountry)
            onSaved()
        }
    }
}