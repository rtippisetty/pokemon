package dev.ranga.pokemon.domain

import dev.ranga.pokemon.api.model.Pokemons
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GetPokemonListUseCaseImplTest {
    private val repository: PokemonRepository = mockk()
    private val suit = GetPokemonListUseCaseImpl(repository)

    @Test
    fun `getPokemons returns valid Pokemons`() = runTest {
        val expected = mockk<Pokemons>()
        coEvery { repository.getPokemonList(any(), any()) } returns expected

        val result = suit.getPokemons(10, 0)

        assertEquals(expected, result)
    }

    @Test
    fun `repository failure throws exception`() = runTest {
        coEvery { repository.getPokemonList(any(), any()) } throws Exception("Repository failure")

        assertThrows<Exception> {
            suit.getPokemons(10, 0)
        }
    }
}