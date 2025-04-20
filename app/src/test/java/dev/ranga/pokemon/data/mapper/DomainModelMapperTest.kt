package dev.ranga.pokemon.data.mapper

import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.api.model.Pokemons
import dev.ranga.pokemon.data.remote.model.PokemonDetailsResponse
import dev.ranga.pokemon.data.remote.model.PokemonListResponse
import dev.ranga.pokemon.data.remote.model.PokemonListResponseResult
import dev.ranga.pokemon.data.remote.model.PokemonSpritesResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DomainModelMapperTest {

    private lateinit var suit: DomainModelMapper

    @BeforeEach
    fun setup() {
        suit = DomainModelMapper()
    }

    @Test
    fun `remotePokemonListToDomain maps correctly`() {
        // Given
        val remotePokemonResponse = PokemonListResponse(
            count = 2,
            next = "nextUrl",
            previous = "previousUrl",
            results = listOf(
                PokemonListResponseResult(name = "bulbasaur"),
                PokemonListResponseResult(name = "ivysaur")
            )
        )

        // When
        val result = suit.remotePokemonListToDomain(remotePokemonResponse)

        // Then
        val expected = Pokemons(names = listOf("bulbasaur", "ivysaur"))
        assertEquals(expected, result)
    }

    @Test
    fun `remotePokemonListToDomain maps empty list correctly`() {
        // Given
        val remotePokemonResponse = PokemonListResponse(
            count = 0,
            next = null,
            previous = null,
            results = emptyList()
        )

        // When
        val result = suit.remotePokemonListToDomain(remotePokemonResponse)

        // Then
        val expected = Pokemons(names = emptyList())
        assertEquals(expected, result)
    }

    @Test
    fun `remotePokemonDetailsToDomain maps correctly`() {
        // Given
        val remotePokemonDetails = PokemonDetailsResponse(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSpritesResponse(frontDefault = "spriteUrl")
        )

        // When
        val result = suit.remotePokemonDetailsToDomain(remotePokemonDetails)

        // Then
        val expected = PokemonDetails(
            id = 1,
            name = "bulbasaur",
            height = 7,
            spriteImageUrl = "spriteUrl"
        )
        assertEquals(expected, result)
    }
    @Test
    fun `remotePokemonDetailsToDomain maps null spriteUrl`() {
        // Given
        val remotePokemonDetails = PokemonDetailsResponse(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSpritesResponse(frontDefault = null)
        )

        // When
        val result = suit.remotePokemonDetailsToDomain(remotePokemonDetails)

        // Then
        val expected = PokemonDetails(
            id = 1,
            name = "bulbasaur",
            height = 7,
            spriteImageUrl = null
        )
        assertEquals(expected, result)
    }
}