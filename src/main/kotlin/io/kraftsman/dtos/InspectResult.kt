package io.kraftsman.dtos

import kotlinx.serialization.Serializable

@Serializable
data class InspectResult(val result: Boolean, val message: String)