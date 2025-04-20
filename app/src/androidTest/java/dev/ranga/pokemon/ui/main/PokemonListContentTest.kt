package dev.ranga.pokemon.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import dev.ranga.pokemon.ui.theme.PokemonTheme
import org.junit.Rule
import org.junit.Test

class PokemonListContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonListContent_displaysPokemonList() {
        // Given
        val pokemonList = listOf("bulbasaur", "ivysaur", "venusaur")
        val isLoading = false
        val error: String? = null

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonListContent(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    error = error,
                    onNextPage = {},
                    onPokemonSelected = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag("PokemonListView").assertIsDisplayed()

        pokemonList.forEach { pokemonName ->
            composeTestRule.onNode(hasText(pokemonName)).assertIsDisplayed()
        }
    }

    @Test
    fun pokemonListContent_displaysLoadingView() {
        // Given
        val pokemonList = emptyList<String>()
        val isLoading = true
        val error: String? = null

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonListContent(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    error = error,
                    onNextPage = {},
                    onPokemonSelected = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag("LoadingView").assertIsDisplayed()
    }

    @Test
    fun pokemonListContent_displaysErrorSnackbar() {
        // Given
        val pokemonList = emptyList<String>()
        val isLoading = false
        val error = "An error occurred"

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonListContent(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    error = error,
                    onNextPage = {},
                    onPokemonSelected = {}
                )
            }
        }

        // Then
        composeTestRule.onNode(hasText(error)).assertIsDisplayed()
    }

    @Test
    fun pokemonListContent_callsOnPokemonSelected() {
        // Given
        val pokemonList = listOf("bulbasaur")
        val isLoading = false
        val error: String? = null
        var onPokemonSelectedCalled = false
        val selectedPokemon = "bulbasaur"

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonListContent(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    error = error,
                    onNextPage = {},
                    onPokemonSelected = { name ->
                        onPokemonSelectedCalled = true
                        assert(name == selectedPokemon)
                    }
                )
            }
        }

        // Then
        composeTestRule.onNode(hasText("bulbasaur")).performClick()
        assert(onPokemonSelectedCalled)
    }

    @Test
    fun pokemonListContent_callsOnNextPage() {
        // Given
        val pokemonList = List(10) { "pokemon$it" }
        val isLoading = false
        val error: String? = null
        var onNextPageCalled = false
        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonListContent(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    error = error,
                    onNextPage = {
                        onNextPageCalled = true
                    },
                    onPokemonSelected = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("PokemonListView").performTouchInput {
            repeat(10) {
                swipeUp(startY = centerY, endY = 0f, durationMillis = 100)
                composeTestRule.waitForIdle()
            }
        }
        assert(onNextPageCalled)
    }
}