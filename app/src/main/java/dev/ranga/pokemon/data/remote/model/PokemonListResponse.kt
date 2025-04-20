package dev.ranga.pokemon.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

@Serializable
data class PokemonListItem(
    val name: String,
    val url: String
)
