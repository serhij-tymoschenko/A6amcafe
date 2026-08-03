package dev.tymoshenko.a6amcafe.data.models.mashi.feed

import dev.tymoshenko.a6amcafe.data.models.mashi.traits.TraitDetails
import dev.tymoshenko.a6amcafe.data.models.users.MashiCreatorDetails

data class MashupMetadata(
    val traits: List<TraitDetails>,
    val creatorDetails: MashiCreatorDetails,
    val mintNumber: Int
)