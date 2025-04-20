package dev.ranga.pokemon.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ranga.pokemon.R
import dev.ranga.pokemon.ui.common.HDivider
import dev.ranga.pokemon.ui.common.LoadingView
import dev.ranga.pokemon.ui.common.PokemonAppBar
import dev.ranga.pokemon.ui.theme.PokemonTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PokemonListScreen(onPokemonSelected: (name: String) -> Unit) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    val pokemonList = viewModel.pokemonList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState(null)

    PokemonListContent(
        pokemonList = pokemonList.value,
        isLoading = isLoading.value,
        error = error.value,
        onNextPage = viewModel::onNextPage,
        onPokemonSelected = onPokemonSelected
    )
}

@Composable
private fun PokemonListContent(
    pokemonList: List<String>,
    isLoading: Boolean,
    error: String?,
    onNextPage: () -> Unit,
    onPokemonSelected: (name: String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { PokemonListTopBar() },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->

        LaunchedEffect(error) {
            error?.let { message ->
                snackBarHostState.showSnackbar(message = message)
            }
        }

        PokemonListView(
            modifier = Modifier.padding(padding),
            pokemonList = pokemonList,
            isLoading = isLoading,
            onNextPage = onNextPage,
            onPokemonSelected = onPokemonSelected
        )
    }
}

@Composable
private fun PokemonListTopBar() {
    PokemonAppBar(
        title = stringResource(R.string.app_name),
    )
}


@Composable
private fun PokemonListView(
    modifier: Modifier = Modifier,
    pokemonList: List<String>,
    isLoading: Boolean,
    onNextPage: () -> Unit,
    onPokemonSelected: (name: String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(PokemonTheme.dimensions.large),
        verticalArrangement = Arrangement.spacedBy(PokemonTheme.dimensions.small)
    ) {
        items(
            items = pokemonList,
            key = { it }
        ) { pokemonName ->
            PokemonListItem(
                name = pokemonName,
                onClick = onPokemonSelected
            )
        }
        if (isLoading) {
            item {
                LoadingView(modifier = Modifier.fillMaxWidth())
            }
        }
    }
    HandleListScroll(
        listState = listState,
        onLoadMore = onNextPage
    )
}

@Composable
private fun HandleListScroll(listState: LazyListState, onLoadMore: () -> Unit) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem.index >= totalItems - 6
        }
    }
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    onLoadMore()
                }
            }
    }
}

@Composable
private fun PokemonListItem(
    name: String,
    onClick: (name: String) -> Unit
) {
    Text(
        modifier = Modifier
            .padding(PokemonTheme.dimensions.large)
            .fillMaxWidth()
            .clickable { onClick(name) },
        text = name,
        style = TextStyle(
            color = Color.Black,
            textAlign = TextAlign.Left,
            fontSize = PokemonTheme.dimensions.fontSizeLarge,
            fontWeight = FontWeight.Normal
        )
    )
    HDivider()
}
