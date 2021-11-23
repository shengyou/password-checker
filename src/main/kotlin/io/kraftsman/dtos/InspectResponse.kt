package io.kraftsman.dtos

import kotlinx.serialization.Serializable

@Serializable
data class InspectResponse(
    val result: Boolean,
    val message: String
)
