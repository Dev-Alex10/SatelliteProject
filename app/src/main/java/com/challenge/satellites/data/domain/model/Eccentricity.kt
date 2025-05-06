package com.challenge.satellites.data.domain.model

enum class Eccentricity {
    ECCENTRICITY_GTE,
    ECCENTRICITY_LTE,
    NONE
}

fun Eccentricity.toApiString(): String? {
    return when (this) {
        Eccentricity.ECCENTRICITY_GTE -> "1"
        Eccentricity.ECCENTRICITY_LTE -> "1"
        Eccentricity.NONE -> null
    }
}