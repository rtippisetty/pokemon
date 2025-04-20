package dev.ranga.pokemon.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: PokemonSpritesResponse,
)

@Serializable
data class PokemonSpritesResponse(
    @SerialName("front_default")
    val frontDefault: String?,
)
