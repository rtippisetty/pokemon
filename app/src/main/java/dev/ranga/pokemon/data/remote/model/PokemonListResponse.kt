package dev.ranga.pokemon.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListResponseResult>
)

@Serializable
data class PokemonListResponseResult(
    val name: String,
)
