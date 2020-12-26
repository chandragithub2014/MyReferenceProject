package com.ref.listtypes.expandable.models

data class StateCapital(
    val countries: List<Country>
) {
    data class Country(
        val country: String, // India
        val states: List<State>
    ) {
        data class State(
            val capital: String, // Hyderabad
            val name: String // Telangana
        )
    }
}