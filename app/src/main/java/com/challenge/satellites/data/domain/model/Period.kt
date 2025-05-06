package com.challenge.satellites.data.domain.model

enum class Period() {
    PERIOD_GT,
    PERIOD_LT,
    NONE
}

fun Period.toApiString(): String? {
    return when (this) {
        Period.PERIOD_GT -> "255"
        Period.PERIOD_LT -> "255"
        Period.NONE -> null
    }
}