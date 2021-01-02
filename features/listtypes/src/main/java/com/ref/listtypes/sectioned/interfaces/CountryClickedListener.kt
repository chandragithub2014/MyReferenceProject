package com.ref.listtypes.sectioned.interfaces

import com.ref.listtypes.expandable.models.StateCapital

interface CountryClickedListener {
    fun onItemClick(countryName : String, countryChild : StateCapital.Country.State )
}