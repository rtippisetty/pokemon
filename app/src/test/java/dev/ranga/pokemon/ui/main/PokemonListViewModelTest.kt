package dev.ranga.pokemon.ui.main

import dev.ranga.pokemon.api.GetPokemonListUseCase
import dev.ranga.pokemon.api.model.Pokemons
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonListViewModelTest {
    private val getPokemonListUseCase: GetPokemonListUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var suit: PokemonListViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial pokemonList emission`() = runTest {
        // Verify that `pokemonList` emits an empty list initially when the ViewModel 
        // is created, before the first fetch.
        val expected = emptyList<String>()

        val viewModel = PokemonListViewModel(getPokemonListUseCase)

        assertEquals(expected, viewModel.pokemonList.value)
    }

    @Test
    fun `successful fetch pokemonList emission`() = runTest {
        // Test that `pokemonList` emits the list of pokemon names after a 
        // successful fetch from `getPokemonListUseCase`.
        val expected = listOf("bulbasaur", "ivysaur", "venusaur")
        coEvery { getPokemonListUseCase.getPokemons(any(), any()) } returns Pokemons(expected)

        suit = PokemonListViewModel(getPokemonListUseCase)

        advanceUntilIdle()

        assertEquals(expected, suit.pokemonList.value)
    }

    @Test
    fun `successful multiple fetch pokemonList emission`() = runTest {
        // Test if `pokemonList` is updated correctly when `onNextPage()`
        // calls happen. Ensure old list is not replaced but appended.
        val mockedPokemonLists = listOf(
            Pokemons(listOf("bulbasaur", "ivysaur", "venusaur")),
            Pokemons(listOf("charmander", "charmeleon", "charizard"))
        )

        mockGetPokemonListUseCase(mockedPokemonLists)

        suit = PokemonListViewModel(getPokemonListUseCase)
        advanceUntilIdle()

        assertEquals(mockedPokemonLists[0].names, suit.pokemonList.value)

        suit.onNextPage()
        advanceUntilIdle()

        assertEquals(listOf(mockedPokemonLists[0].names, mockedPokemonLists[1].names).flatten(), suit.pokemonList.value)
    }

    @Test
    fun `Initial isLoading emission`() {
        // Verify that `isLoading` emits `false` initially when the ViewModel 
        // is created, before the first fetch.
        suit = PokemonListViewModel(getPokemonListUseCase)

        assertEquals(false, suit.isLoading.value)
    }

    @Test
    fun `isLoading during fetch`() = runTest {
        // Check that `isLoading` emits `true` at the beginning of 
        // `fetchPokemonList` and `false` when it completes (success or failure).
        mockGetPokemonListUseCase(listOf(Pokemons(listOf("bulbasaur"))), delay = 1000L)

        suit = PokemonListViewModel(getPokemonListUseCase)

        advanceTimeBy(500)
        assertEquals(true, suit.isLoading.value)

        advanceUntilIdle()
        assertEquals(false, suit.isLoading.value)
    }

    @Test
    fun `isLoading remains false after fetch fails`() = runTest {
        // Ensure that `isLoading` remains false after a completed fetch 
        // operation fails.
        mockGetPokemonListUseCaseFailure(Exception("Fetch failed"))

        suit = PokemonListViewModel(getPokemonListUseCase)

        assertEquals(false, suit.isLoading.value)
        advanceUntilIdle()
        assertEquals(false, suit.isLoading.value)
    }

    @Test
    fun `error emission on Exception`() = runTest {
        // Test that `error` emits a string message when `getPokemonListUseCase` 
        // throws an exception. Verify the message.
        val expected = "Fetch failed"
        mockGetPokemonListUseCaseFailure(Exception(expected))

        suit = PokemonListViewModel(getPokemonListUseCase)

        var actual = suit.error.firstOrNull()

        assertEquals(expected, actual)
    }

    @Test
    fun `Concurrent onNextPage calls`() = runTest {
        // Check how the ViewModel behaves when multiple `onNextPage()` calls
        // happen concurrently. Check for race conditions.
        val mockedPokemonLists = listOf(
            Pokemons(listOf("bulbasaur", "ivysaur", "venusaur")),
            Pokemons(listOf("charmander", "charmeleon", "charizard")),
            Pokemons(listOf("squirtle", "wartortle", "blastoise"))
        )
        val delay = 1000L
        mockGetPokemonListUseCase(mockedPokemonLists, delay)

        suit = PokemonListViewModel(getPokemonListUseCase)
        advanceUntilIdle()

        val job1 = launch {
            suit.onNextPage()
        }
        val job2 = launch {
            suit.onNextPage()
        }

        advanceUntilIdle()
        job1.join()
        job2.join()
        //Expected value
        val expected = listOf(mockedPokemonLists[0].names, mockedPokemonLists[1].names, mockedPokemonLists[2].names).flatten()

        assertEquals(expected, suit.pokemonList.value)
    }

    private fun mockGetPokemonListUseCase(
        expected: List<Pokemons>,
        delay: Long = 0L
    ) {
        var count = 0
        coEvery { getPokemonListUseCase.getPokemons(any(), any()) } coAnswers {
            delay(delay)
            expected[count++]
        }
    }

    private fun mockGetPokemonListUseCaseFailure(
        exception: Exception
    ) {
        coEvery { getPokemonListUseCase.getPokemons(any(), any()) } throws exception
    }
}