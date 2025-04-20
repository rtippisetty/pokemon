package dev.ranga.pokemon.domain

import dev.ranga.pokemon.api.GetPokemonListUseCase
import dev.ranga.pokemon.api.model.Pokemons
import javax.inject.Inject

class GetPokemonListUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
) : GetPokemonListUseCase {
    override suspend fun getPokemons(
        limit: Int,
        offset: Int
    ): Pokemons {
        return repository.getPokemonList(limit, offset)
    }
}