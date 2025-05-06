package com.challenge.satellites.data.domain.model

enum class Inclination {
    RETROGADE,
    POSIGRADE,
    NONE
}
fun Inclination.toApiString(): String? {
    return when (this) {
        Inclination.RETROGADE -> "90"
        Inclination.POSIGRADE -> "90"
        Inclination.NONE -> null
    }
}