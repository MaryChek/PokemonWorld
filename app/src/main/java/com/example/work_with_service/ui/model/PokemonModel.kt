package com.example.work_with_service.ui.model

import com.example.work_with_service.data.service.PokemonService
import com.example.work_with_service.data.entities.Ability
import com.example.work_with_service.data.entities.NameResource
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.data.service.Status

class PokemonModel {
    private var onListReadyListener: ((PokiAttributes) -> Unit)? = null
    private var pokemonService: PokemonService = PokemonService()
    private var pokemonList: List<Pokemon> = listOf()
    private var pokemon: Pokemon? = null
    private var pokemonDetail: PokemonInfo? = null

    fun createPokemonList(
        onPokemonListReadyListener: (PokiAttributes) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        onListReadyListener = onPokemonListReadyListener
        pokemonService.callPokemonSource(this::onServiceFinishedWork, onServiceReturnError)
    }

    fun createPokemonInfo(
        namePokemon: String,
        onPokemonInfoReadyListener: (PokiAttributes) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        onListReadyListener = onPokemonInfoReadyListener
        pokemon = getPokemonByName(namePokemon)
        pokemon?.let {
            pokemonService.callPokemonInfo(it, this::onServiceFinishedWork, onServiceReturnError)
        }
    }

    private fun getPokemonByName(namePokemon: String): Pokemon =
        pokemonList.first {
            it.name == namePokemon
        }

    fun isPokemonListAttributesEmpty(): Boolean =
        pokemonList.isEmpty()

    fun getListPokemonAttributes(): ListPokemonAttributes =
        getListPokemonAttributes(pokemonList)

    fun getPokemonDetail(): PokemonInfo? =
        pokemonDetail

    private fun onServiceFinishedWork(pokemonAnswer: ServicePokemonAnswer) {
        when (pokemonAnswer) {
            is ListPokemon -> {
                pokemonList = pokemonAnswer.listPokemon
                onListReadyListener?.invoke(getListPokemonAttributes(pokemonList))
            }
            is PokiInfo ->
                onListReadyListener?.invoke(getPokemonInfo(pokemonAnswer))
        }
    }

    private fun getListPokemonAttributes(listPokemon: List<Pokemon>): ListPokemonAttributes {
        val listAttributes: MutableList<PokemonAttributes> = mutableListOf()
        listPokemon.forEach {
            listAttributes.add(PokemonAttributes(it.sprites.frontDefault, it.name))
        }
        return ListPokemonAttributes(listAttributes)
    }

    private fun getPokemonInfo(pokiInfo: PokiInfo): PokemonInfo =
        PokemonInfo(
            pokemon?.sprites?.frontDefault,
            takeBaseInfo(pokiInfo),
            takeAbilitiesInfo(pokiInfo.abilities),
            takeTypesInfo(pokiInfo)
        )

    private fun takeBaseInfo(pokiInfo: PokiInfo): BaseInfo =
        BaseInfo(
            pokemon?.name ?: "",
            pokemon?.baseExperience ?: 0,
            pokiInfo.pokemonSpecies.captureRate,
            pokemon?.height ?: 0,
            pokemon?.weight ?: 0,
            pokiInfo.pokemonSpecies.isBaby,
            pokiInfo.pokemonSpecies.habitat.name,
            pokiInfo.pokemonSpecies.color.name
        )


    private fun takeAbilitiesInfo(abilities: List<Ability>): List<PokiAbility> {
        val abilitiesInfo: MutableList<PokiAbility> = mutableListOf()
        abilities.forEach { ability ->
            ability.effectEntries.firstOrNull {
                it.language.name == ENGLISH_LANGUAGE
            }?.let {
                abilitiesInfo.add(PokiAbility(ability.name, it.effect))
            }
        }
        return abilitiesInfo
    }

    private fun takeTypesInfo(pokiInfo: PokiInfo): List<PokiType> {
        val typesInfo: MutableList<PokiType> = mutableListOf()
        pokiInfo.types.forEach { type ->
            typesInfo.add(
                PokiType(
                    type.name,
                    takeDamageFrom(type.damageRelations.noDamageTo),
                    takeDamageFrom(type.damageRelations.doubleDamageTo),
                    takeDamageFrom(type.damageRelations.noDamageFrom),
                    takeDamageFrom(type.damageRelations.doubleDamageFrom)
                )
            )
        }
        return typesInfo
    }

    private fun takeDamageFrom(damageApiResource: List<NameResource>): List<String> {
        val damage: MutableList<String> = mutableListOf()
        damageApiResource.forEach {
            damage.add(it.name)
        }
        return damage
    }

    companion object {
        private const val ENGLISH_LANGUAGE = "en"
    }
}