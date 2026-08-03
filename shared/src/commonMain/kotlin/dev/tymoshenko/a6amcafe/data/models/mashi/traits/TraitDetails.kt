package dev.tymoshenko.a6amcafe.data.models.mashi.traits

import kotlinx.serialization.Serializable

@Serializable
data class TraitDetails(
    val type: TraitType,
    val url: String? = null
)


