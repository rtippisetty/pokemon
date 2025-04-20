package dev.ranga.pokemon.domain

import dev.ranga.pokemon.api.model.PokemonDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GetPokemonDetailsUseCaseImplTest {
    private val repository: PokemonRepository = mockk()
    private val suit = GetPokemonDetailsUseCaseImpl(repository)

    @Test
    fun `use case returns valid PokemonDetails`() = runTest {
        val expected = mockk<PokemonDetails>()
        coEvery { repository.getPokemonDetails(any()) } returns expected

        val result = suit.details("bulbasaur")

        assertEquals(expected, result)
    }

    @Test
    fun `non existent Pokemon name throws exception`() = runTest {
        coEvery { repository.getPokemonDetails(any()) } throws Exception("Pokemon not found")

        assertThrows<Exception> {
            suit.details("non-existent-pokemon")
        }
    }

    @Test
    fun `repository throws exception`() = runTest {
        coEvery { repository.getPokemonDetails(any()) } throws Exception("Repository failure")

        assertThrows<Exception> {
            suit.details("bulbasaur")
        }
    }
}