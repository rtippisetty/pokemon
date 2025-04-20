package dev.ranga.pokemon.ui.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dev.ranga.pokemon.ui.detail.PokemonDetailsContent
import dev.ranga.pokemon.ui.detail.model.PokemonDetailsState
import dev.ranga.pokemon.ui.detail.model.UIPokemonDetails
import dev.ranga.pokemon.ui.theme.PokemonTheme
import org.junit.Rule
import org.junit.Test

internal class PokemonDetailsContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonDetailsContent_displaysLoadingState() {
        // Given
        val state = PokemonDetailsState.Loading

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonDetailsContent(state = state, onBack = { })
            }
        }

        // Then
        composeTestRule.onNodeWithTag("LoadingView").assertIsDisplayed()
    }

    @Test
    fun pokemonDetailsContent_displaysErrorState() {
        // Given
        val errorMessage = "An error occurred"
        val state = PokemonDetailsState.Error(errorMessage)

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonDetailsContent(state = state, onBack = { })
            }
        }

        // Then
        composeTestRule.onNodeWithTag("ErrorView").assertIsDisplayed()
        composeTestRule.onNode(hasText(errorMessage)).assertIsDisplayed()
    }

    @Test
    fun pokemonDetailsContent_displaysSuccessState() {
        // Given
        val pokemonDetails = UIPokemonDetails("bulbasaur", "someUrl", 7)
        val state = PokemonDetailsState.Success(pokemonDetails)

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonDetailsContent(state = state, onBack = { })
            }
        }

        // Then
        composeTestRule.onNodeWithTag("PokemonDetailsView").assertIsDisplayed()
        composeTestRule.onNode(hasText("name: bulbasaur")).assertIsDisplayed()
        composeTestRule.onNode(hasText("height: 7")).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PokemonImageView").assertIsDisplayed()
    }

    @Test
    fun pokemonDetailsContent_displaysSuccessState_withoutImage() {
        // Given
        val pokemonDetails = UIPokemonDetails("bulbasaur", null, 7)
        val state = PokemonDetailsState.Success(pokemonDetails)

        // When
        composeTestRule.setContent {
            PokemonTheme {
                PokemonDetailsContent(state = state, onBack = { })
            }
        }

        // Then
        composeTestRule.onNodeWithTag("PokemonDetailsView").assertIsDisplayed()
        composeTestRule.onNode(hasText("name: bulbasaur")).assertIsDisplayed()
        composeTestRule.onNode(hasText("height: 7")).assertIsDisplayed()
    }
}