package com.example.worldfavorites.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.SaveAs
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.worldfavorites.view.components.CountryItemState
import com.example.worldfavorites.view.viewmodel.CountryDetailsViewModel

data class CountryDetailsSceneState(
    val country: CountryItemState
)

val sampleCountryDetailsSceneState = CountryDetailsSceneState(
    country = CountryItemState(name = "USA")
)

@Composable
fun CountryDetailsScene(
    viewState: CountryDetailsSceneState,
    onNavEvent: () -> Unit,
    isEditMode: Boolean = false,
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    val description by viewModel.description.collectAsStateWithLifecycle()

    // Load existing country data if in edit mode, otherwise reset
    LaunchedEffect(viewState.country.name, isEditMode) {
        if (isEditMode) {
            viewModel.loadExistingCountry(viewState.country)
        } else {
            viewModel.setCountryData(viewState.country)
            viewModel.resetDescription()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = viewState.country.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        viewModel.saveFavoriteCountry(viewState.country.name) {
                            onNavEvent()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isEditMode) Icons.Outlined.SaveAs else Icons.Outlined.Save,
                        contentDescription = if (isEditMode) "Update favorite" else "Save to favorites",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row {
            viewState.country.isoCode?.let { isoCode ->
                Text(
                    text = "$isoCode •",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            viewState.country.capital?.let { capital ->
                Text(
                    text = "$capital",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        viewState.country.region?.let { region ->
            Text(
                text = "$region",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("My favorite things about ${viewState.country.name}") },
            placeholder = { Text("Add a description...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}
