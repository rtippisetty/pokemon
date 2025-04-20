package dev.ranga.pokemon.domain

import dev.ranga.pokemon.api.GetPokemonDetailsUseCase
import dev.ranga.pokemon.api.model.PokemonDetails
import javax.inject.Inject

class GetPokemonDetailsUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
) : GetPokemonDetailsUseCase {
    override suspend fun details(name: String): PokemonDetails {
        return repository.getPokemonDetails(name)
    }
}