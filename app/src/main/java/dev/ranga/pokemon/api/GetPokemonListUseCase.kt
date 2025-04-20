package dev.ranga.pokemon.api

import dev.ranga.pokemon.api.model.Pokemons

interface GetPokemonListUseCase {
    suspend fun getPokemons(limit: Int, offset: Int): Pokemons
}