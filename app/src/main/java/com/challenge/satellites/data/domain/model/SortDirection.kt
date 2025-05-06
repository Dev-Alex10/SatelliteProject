package com.challenge.satellites.data.domain.model

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