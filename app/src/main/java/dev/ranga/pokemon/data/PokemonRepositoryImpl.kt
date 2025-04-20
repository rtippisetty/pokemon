package dev.ranga.pokemon.data

import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.api.model.Pokemons
import dev.ranga.pokemon.data.mapper.DomainModelMapper
import dev.ranga.pokemon.data.remote.PokemonService
import dev.ranga.pokemon.domain.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val domainModelMapper: DomainModelMapper
) : PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Pokemons {
        val remotePokemonResponse = pokemonService.getPokemonList(limit, offset)
        return domainModelMapper.remotePokemonListToDomain(remotePokemonResponse)
    }

    override suspend fun getPokemonDetails(name: String): PokemonDetails {
        val remotePokemonDetails = pokemonService.getPokemonDetails(name)
        return domainModelMapper.remotePokemonDetailsToDomain(remotePokemonDetails)
    }
}