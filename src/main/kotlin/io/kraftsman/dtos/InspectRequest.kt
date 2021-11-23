package io.kraftsman.dtos

import kotlinx.serialization.Serializable

@Serializable
data class InspectRequest(
    val password: String
)
