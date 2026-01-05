package com.example.worldfavorites.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldfavorites.data.local.FavoriteCountryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Home Screen State
interface HomeScreenState {
    data class Success(val favoriteCountries: List<CountryItemState>? = null) : HomeScreenState
    data class Error(val errorMessage: String = "") : HomeScreenState
    object Loading : HomeScreenState
}

@HiltViewModel
class HomeSceneViewModel @Inject constructor(
    private val favoriteCountryDao: FavoriteCountryDao
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val viewState: StateFlow<HomeScreenState> = _viewState.asStateFlow()

    init {
        loadFavoriteCountries()
    }

    private fun loadFavoriteCountries() {
        viewModelScope.launch {
            _viewState.value = HomeScreenState.Loading
            try {
                favoriteCountryDao.getAllFavoriteCountries().collect { entities ->
                    val countryItems = entities.map { entity ->
                        CountryItemState(
                            name = entity.name,
                            description = entity.description,
                            capital = entity.capital,
                            region = entity.region,
                            isoCode = entity.isoCode,
                            status = CountryItemStatus.EXPANDABLE
                        )
                    }
                    _viewState.value = HomeScreenState.Success(favoriteCountries = countryItems)
                }
            } catch (e: Exception) {
                _viewState.value = HomeScreenState.Error(errorMessage = e.message ?: "An error occurred")
            }
        }
    }

    fun deleteFavoriteCountry(countryName: String) {
        viewModelScope.launch {
            favoriteCountryDao.deleteFavoriteCountry(countryName)
        }
    }
}