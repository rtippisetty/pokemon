package dev.ranga.pokemon.data.mapper

import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.api.model.Pokemons
import dev.ranga.pokemon.data.remote.model.PokemonDetailsResponse
import dev.ranga.pokemon.data.remote.model.PokemonListResponse
import javax.inject.Inject

class DomainModelMapper @Inject constructor() {
    fun remotePokemonListToDomain(remotePokemonResponse: PokemonListResponse): Pokemons {
        return Pokemons(
            names = remotePokemonResponse.results.map { it.name }
        )
    }

    fun remotePokemonDetailsToDomain(remotePokemonDetails: PokemonDetailsResponse): PokemonDetails {
        return PokemonDetails(
            id = remotePokemonDetails.id,
            name = remotePokemonDetails.name,
            height = remotePokemonDetails.height,
            spriteImageUrl = remotePokemonDetails.sprites.frontDefault
        )
    }
}