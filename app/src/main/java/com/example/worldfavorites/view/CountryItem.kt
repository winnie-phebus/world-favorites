package com.example.worldfavorites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class CountryItemStatus {
    EXPANDABLE, COMPACT, NONE
}

enum class CountryItemActionIcon(
    val image: ImageVector,
    val description: String,
    val color: Color = Color.White
){
    EDIT(
        image = Icons.Default.Edit,
        description = "EDIT",
    ),

    DELETE(
        image = Icons.Default.Close,
        description = "DELETE"
    )
}

data class CountryItemAction(
    val icon: CountryItemActionIcon,
    val onClick: () -> Unit
)

data class CountryItemState(
    val name: String,
    val description: String? = null,
    val capital: String? = null,
    val region: String? = null,
    val isoCode: String? = null,
    val status: CountryItemStatus = CountryItemStatus.NONE
)

@Composable
fun CountryItem(
    viewState: CountryItemState,
    actions: List<CountryItemAction>? = null,
    onClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(4.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 16.dp)
            .padding(start= 16.dp, end= 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    viewState.name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Show capital & ISO code if expandable and expanded
                if (viewState.status == CountryItemStatus.EXPANDABLE && isExpanded) {
                    Row {
                        viewState.isoCode?.let { isoCode ->
                            Text(
                                text = "$isoCode •",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }

                        viewState.capital?.let { capital ->
                            Text(
                                text = capital,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

            actions?.let {
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(1.dp)
                ) {
                    actions.forEach { action ->
                        IconButton(action.onClick, modifier = Modifier.size(32.dp)) {
                            Icon(
                                imageVector = action.icon.image,
                                contentDescription = action.icon.description,
                                tint = action.icon.color,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            if (viewState.status == CountryItemStatus.EXPANDABLE && !viewState.description.isNullOrEmpty()) {
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (viewState.status != CountryItemStatus.COMPACT) {
            if (!viewState.description.isNullOrEmpty()) {
                if (viewState.status == CountryItemStatus.EXPANDABLE && isExpanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = viewState.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(end = 8.dp),
                    maxLines = if (viewState.status == CountryItemStatus.EXPANDABLE && !isExpanded) 1 else Int.MAX_VALUE,
                    overflow = if (viewState.status == CountryItemStatus.EXPANDABLE && !isExpanded) TextOverflow.Ellipsis else TextOverflow.Clip
                )
            }

            if (viewState.status == CountryItemStatus.EXPANDABLE && isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                viewState.region?.let { region ->
                    Text(
                        text = region,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(end = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

val sampleCountryItemStates = listOf(
    CountryItemState(
        name = "United States of America",
        description = "Where I live!",
        capital = "Washington, D.C.",
        region = "North America",
        isoCode = "US",
        status = CountryItemStatus.EXPANDABLE
    ),
    CountryItemState(
        name = "Jamaica",
        capital = "Kingston",
        region = "Latin America & Caribbean",
        isoCode = "JM"
    )
)

@Preview
@Composable
fun CountryItemPreview() {
    Column {
        sampleCountryItemStates.forEach {
            CountryItem(it.copy(status = CountryItemStatus.COMPACT), onClick = {})
            CountryItem(it, onClick = {}, actions = CountryItemActionIcon.entries.map { icon -> CountryItemAction(icon) {} })
        }
    }
}
