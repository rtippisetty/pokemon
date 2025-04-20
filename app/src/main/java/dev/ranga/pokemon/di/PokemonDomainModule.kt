package dev.ranga.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.ranga.pokemon.api.GetPokemonDetailsUseCase
import dev.ranga.pokemon.api.GetPokemonsUseCase
import dev.ranga.pokemon.domain.GetPokemonDetailsUseCaseImpl
import dev.ranga.pokemon.domain.GetPokemonsUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface PokemonDomainModule {

    @Binds
    fun bindGetPokemonsUseCase(impl: GetPokemonsUseCaseImpl): GetPokemonsUseCase

    @Binds
    fun bindGetPokemonDetailsUseCase(impl: GetPokemonDetailsUseCaseImpl): GetPokemonDetailsUseCase

}