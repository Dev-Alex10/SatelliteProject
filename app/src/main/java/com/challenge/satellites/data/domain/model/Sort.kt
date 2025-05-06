package com.challenge.satellites.data.domain.model

enum class Sort {
    ID,
    NAME,
    POPULARITY,
    INCLINATION,
    ECCENTRICITY,
    PERIOD
}
fun Sort.toApiString(): String {
    return when (this) {
        Sort.ID -> "id"
        Sort.NAME -> "name"
        Sort.POPULARITY -> "popularity"
        Sort.INCLINATION -> "inclination"
        Sort.ECCENTRICITY -> "eccentricity"
        Sort.PERIOD -> "period"
    }
}
