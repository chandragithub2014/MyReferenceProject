package com.ref.listtypes.expandable.repository

import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.utils.JSONHelper

class CountryStateRepository {
    fun fetchCountryStateCapitals() : StateCapital = JSONHelper.fetchParsedJSONInfoForStateCapitals()
}