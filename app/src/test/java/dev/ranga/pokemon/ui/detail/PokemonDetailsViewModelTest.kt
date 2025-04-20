package dev.ranga.pokemon.ui.detail

import androidx.lifecycle.SavedStateHandle
import dev.ranga.pokemon.api.GetPokemonDetailsUseCase
import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.ui.detail.model.PokemonDetailsState
import dev.ranga.pokemon.ui.detail.model.UIPokemonDetails
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonDetailsViewModelTest {
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val uiPokemonDetailsMapper: UIPokemonDetailsMapper = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var suit: PokemonDetailsViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        suit = PokemonDetailsViewModel(
            savedStateHandle,
            getPokemonDetailsUseCase,
            uiPokemonDetailsMapper
        )
    }

    private fun mockSavedStateHandle(name: String?) {
        every { savedStateHandle.get<String>(any()) } returns name
    }

    private fun mockGetPokemonDetailsUseCase(pokemonDetails: PokemonDetails) {
        coEvery { getPokemonDetailsUseCase.details(any()) } returns pokemonDetails
    }

    private fun mockUIPokemonDetailsMapper(uiPokemonDetails: UIPokemonDetails) {
        every { uiPokemonDetailsMapper.map(any()) } returns uiPokemonDetails
    }

    @Test
    fun `getPokemonDetails with valid name`() = runTest {
        // Verify that when a valid Pokemon name is provided, the function
        // successfully fetches and returns the Pokemon details in a Success state.
        mockSavedStateHandle("bulbasaur")
        mockGetPokemonDetailsUseCase(mockk())
        val expected = mockk<UIPokemonDetails>()
        mockUIPokemonDetailsMapper(expected)

        initViewModel()
        advanceUntilIdle()

        assertEquals(PokemonDetailsState.Success(expected), suit.pokemonDetails.value)
    }

    @TestFactory
    fun `getPokemonDetails with invalid name`(): Collection<DynamicTest> {
        // Verify that when an invalid name (null, empty, or whitespace) is provided,
        // the function returns an Error state with the message "Invalid pokemon name".
        val invalidNames = listOf<String?>(null, "", " ", "   ")

        return invalidNames.map { name ->
            DynamicTest.dynamicTest("getPokemonDetails with invalid name: '$name'") {
                mockSavedStateHandle(name)

                initViewModel()
                runTest {
                    advanceUntilIdle()

                    assertEquals(
                        PokemonDetailsState.Error("Invalid pokemon name"),
                        suit.pokemonDetails.value
                    )
                }
            }
        }
    }

    @Test
    fun `getPokemonDetails loading state`() {
        // Verify that the function initially emits a Loading state
        // before fetching data.
        mockSavedStateHandle("bulbasaur")
        mockGetPokemonDetailsUseCase(mockk())
        mockUIPokemonDetailsMapper(mockk())

        initViewModel()

        assertEquals(PokemonDetailsState.Loading, suit.pokemonDetails.value)
    }

    @Test
    fun `getPokemonDetails use case exception`() = runTest {
        // Verify that when the GetPokemonDetailsUseCase throws an exception,
        // the function returns an Error state with the exception message.
        mockSavedStateHandle("bulbasaur")
        coEvery { getPokemonDetailsUseCase.details(any()) } throws Exception("Fetch failed")
        mockUIPokemonDetailsMapper(mockk())

        initViewModel()
        advanceUntilIdle()

        assertEquals(PokemonDetailsState.Error("Fetch failed"), suit.pokemonDetails.value)
    }

    @Test
    fun `getPokemonDetails unknown exception`() = runTest {
        // Verify that when an unknown exception occurs during fetching,
        // the function returns an Error state with the message "Unknown error".
        mockSavedStateHandle("bulbasaur")
        coEvery { getPokemonDetailsUseCase.details(any()) } throws Exception()
        mockUIPokemonDetailsMapper(mockk())

        initViewModel()
        advanceUntilIdle()

        assertEquals(PokemonDetailsState.Error("Unknown error"), suit.pokemonDetails.value)
    }

}