package com.challenge.satellites.data.remote.satellite.model

enum class SortDirection {
    ASC,
    DESC
}
fun SortDirection.toApiString(): String {
    return when (this) {
        SortDirection.ASC -> "asc"
        SortDirection.DESC -> "desc"
    }
}