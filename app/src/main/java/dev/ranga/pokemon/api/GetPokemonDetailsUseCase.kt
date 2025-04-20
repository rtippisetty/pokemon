package dev.ranga.pokemon.api

import dev.ranga.pokemon.api.model.PokemonDetails

interface GetPokemonDetailsUseCase {
    suspend fun details(name: String): PokemonDetails
}