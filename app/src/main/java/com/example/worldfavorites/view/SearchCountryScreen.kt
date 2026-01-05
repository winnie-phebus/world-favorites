package com.example.worldfavorites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.worldfavorites.view.components.AppText

val sampleAllCountries = listOf(
    CountryItemState("USA"),
    CountryItemState(name = "Canada"),
    CountryItemState(name = "France"),
    CountryItemState("Spain")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCountryScreen(
    viewState: SearchCountryViewState,
    onNavAction: () -> Unit,
    onCountryClick: (CountryItemState) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .statusBarsPadding()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavAction) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search countries...", style = MaterialTheme.typography.bodyMedium) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
                    cursorColor = MaterialTheme.colorScheme.onSecondary
                )
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        when (viewState) {
            is SearchCountryViewState.Success -> {
                // Use derivedStateOf to only recalculate when searchQuery or countries change
                val filteredCountries by remember(viewState.allCountries) {
                    derivedStateOf {
                        viewState.allCountries?.filter { country ->
                            country.name.contains(searchQuery, ignoreCase = true)
                        } ?: emptyList()
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(
                        items = filteredCountries,
                        key = { country -> country.name }
                    ) { country ->
                        CountryItem(country, onClick = { onCountryClick(country) })
                    }
                }
            }
            is SearchCountryViewState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        text = viewState.errorMessage.ifEmpty { "An error occurred" },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is SearchCountryViewState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}