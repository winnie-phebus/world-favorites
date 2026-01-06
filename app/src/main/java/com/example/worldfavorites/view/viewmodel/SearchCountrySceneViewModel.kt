package com.example.worldfavorites.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldfavorites.network.WorldFavoritesApi
import com.example.worldfavorites.view.components.CountryItemState
import com.example.worldfavorites.view.toCountryItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchCountryViewState {
    data class Success( val allCountries : List<CountryItemState>? = null ): SearchCountryViewState
    data class Error(val errorMessage: String = "") : SearchCountryViewState
    object Loading : SearchCountryViewState
}

@HiltViewModel
class SearchCountrySceneViewModel @Inject constructor(): ViewModel(){
    private val _viewState = MutableStateFlow<SearchCountryViewState>(SearchCountryViewState.Loading)
    val viewState: StateFlow<SearchCountryViewState> = _viewState.asStateFlow()

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            _viewState.value = SearchCountryViewState.Loading
            _viewState.value = try {
                val response = WorldFavoritesApi.getCountries()

                SearchCountryViewState.Success(
                    allCountries = response.countries.map { country ->
                        country.toCountryItemState()
                    }
                )
            } catch (e: Exception) {
                SearchCountryViewState.Error(errorMessage = e.message.toString())
            }
        }
    }
}