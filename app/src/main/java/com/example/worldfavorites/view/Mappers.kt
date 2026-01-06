package com.example.worldfavorites.view

import com.example.worldfavorites.network.Country
import com.example.worldfavorites.view.components.CountryItemState
import com.example.worldfavorites.view.components.CountryItemStatus

fun Country.toCountryItemState(): CountryItemState {
    return CountryItemState(
        name = this.name,
        description = null,
        capital = this.capitalCity,
        region = this.region.value,
        isoCode = this.iso2Code,
        status = CountryItemStatus.COMPACT
    )
}
