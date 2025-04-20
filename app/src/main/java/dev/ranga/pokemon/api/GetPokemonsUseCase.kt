package dev.ranga.pokemon.api

import dev.ranga.pokemon.api.model.Pokemons

interface GetPokemonsUseCase {
    suspend fun get(limit: Int, offset: Int): Pokemons
}