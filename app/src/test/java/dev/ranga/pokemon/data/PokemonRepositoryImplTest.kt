package dev.ranga.pokemon.data

import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.api.model.Pokemons
import dev.ranga.pokemon.data.mapper.DomainModelMapper
import dev.ranga.pokemon.data.remote.PokemonService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PokemonRepositoryImplTest {
    private val pokemonService: PokemonService = mockk(relaxed = true)
    private val domainModelMapper: DomainModelMapper = mockk(relaxed = true)
    private val suit = PokemonRepositoryImpl(pokemonService, domainModelMapper)

    @Test
    fun `getPokemonList returns valid Pokemons`() = runTest {
        // Test with valid positive integer values for limit and offset.
        // Check if the function returns a Pokemons object.
        val expected = mockk<Pokemons>()
        coEvery { pokemonService.getPokemonList(any(), any()) } returns mockk()
        coEvery { domainModelMapper.remotePokemonListToDomain(any()) } returns expected

        val result = suit.getPokemonList(10, 0)

        assertEquals(expected, result)
    }

    @Test
    fun `getPokemonList API failure`() = runTest {
        // Test when the underlying pokemonService.getPokemonList fails.
        // Check if the function correctly throws an error when the API call fails.
        coEvery { pokemonService.getPokemonList(any(), any()) } throws Exception("API failure")

        assertThrows<Exception> {
            suit.getPokemonList(10, 0)
        }
    }

    @Test
    fun `getPokemonList data mapping failure`() = runTest {
        // Test when the domainModelMapper.remotePokemonListToDomain fails.
        // Check if the function throws an error if there is an issue with data mapping.
        coEvery { pokemonService.getPokemonList(any(), any()) } returns mockk()
        coEvery { domainModelMapper.remotePokemonListToDomain(any()) } throws Exception("Data mapping failure")

        assertThrows<Exception> {
            suit.getPokemonList(10, 0)
        }
    }

    @Test
    fun `getPokemonDetails returns valid PokemonDetails`() = runTest {
        // Test with a valid Pokemon name.
        // Check if the function returns a PokemonDetails object.
        val expected = mockk<PokemonDetails>()
        coEvery { pokemonService.getPokemonDetails(any()) } returns mockk()
        coEvery { domainModelMapper.remotePokemonDetailsToDomain(any()) } returns expected

        val result = suit.getPokemonDetails("bulbasaur")

        assertEquals(expected, result)
    }

    @Test
    fun `getPokemonDetails API failure`() = runTest {
        // Test when the underlying pokemonService.getPokemonDetails fails.
        // Check if the function correctly throws or returns an error when the API call fails.
        coEvery { pokemonService.getPokemonDetails(any()) } throws Exception("API failure")

        assertThrows<Exception> {
            suit.getPokemonDetails("bulbasaur")
        }
    }

    @Test
    fun `getPokemonDetails data mapping failure`() = runTest {
        // Test when the domainModelMapper.remotePokemonDetailsToDomain fails.
        // Check if the function throws or returns an error if there is an issue with data mapping.
        coEvery { pokemonService.getPokemonDetails(any()) } returns mockk()
        coEvery { domainModelMapper.remotePokemonDetailsToDomain(any()) } throws Exception("Data mapping failure")

        assertThrows<Exception> {
            suit.getPokemonDetails("bulbasaur")
        }
    }
}