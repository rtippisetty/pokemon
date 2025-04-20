package dev.ranga.pokemon.domain

import dev.ranga.pokemon.api.GetPokemonsUseCase
import dev.ranga.pokemon.api.model.Pokemons
import javax.inject.Inject

class GetPokemonsUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
) : GetPokemonsUseCase {
    override suspend fun get(
        limit: Int,
        offset: Int
    ): Pokemons {
        return repository.getPokemonList(limit, offset)
    }
}