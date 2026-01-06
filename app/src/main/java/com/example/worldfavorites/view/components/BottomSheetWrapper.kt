package com.example.worldfavorites.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.example.worldfavorites.view.CountryDetailsScene
import com.example.worldfavorites.view.CountryDetailsSceneState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailsBottomSheet(
    country: CountryItemState?,
    isEditMode: Boolean = false,
    onDismiss: () -> Unit
) {
    if (country != null) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            CountryDetailsScene(
                viewState = CountryDetailsSceneState(country = country),
                isEditMode = isEditMode,
                onNavEvent = onDismiss
            )
        }
    }
}
