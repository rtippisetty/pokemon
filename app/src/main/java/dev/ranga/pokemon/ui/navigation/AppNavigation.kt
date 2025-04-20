package dev.ranga.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.ranga.pokemon.ui.detail.PokemonDetailsScreen
import dev.ranga.pokemon.ui.main.PokemonListScreen
import kotlinx.serialization.Serializable

@Serializable
object PokemonListDestination

@Serializable
data class PokemonDetailsDestination(
    val name: String
)

private fun NavGraphBuilder.pokemonListScreen(
    navigateToPokemonDetails: (name: String) -> Unit
) {
    composable<PokemonListDestination> {
        PokemonListScreen(navigateToPokemonDetails)
    }
}

private fun NavGraphBuilder.pokemonDetailsScreen(
    navigateToPokemonList: () -> Unit
) {
    composable<PokemonDetailsDestination> {
        PokemonDetailsScreen(navigateToPokemonList)
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PokemonListDestination
    ) {
        pokemonListScreen(
            navigateToPokemonDetails = { name ->
                navController.navigate(PokemonDetailsDestination(name))
            }
        )

        pokemonDetailsScreen(
            navigateToPokemonList = {
                navController.navigate(PokemonListDestination)
            }
        )
    }
}
