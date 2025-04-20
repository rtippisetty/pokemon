package dev.ranga.pokemon.domain

import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.api.model.Pokemons

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Pokemons
    suspend fun getPokemonDetails(name: String): PokemonDetails
}