package com.example.work_with_service.ui.model

import com.example.work_with_service.R
import com.example.work_with_service.data.service.PokemonService
import com.example.work_with_service.data.entities.Ability
import com.example.work_with_service.data.entities.NameResource
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.data.service.Status
import com.example.work_with_service.ui.fragment.PokemonDetailPageFragment
import java.util.*

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
            it.name.equals(namePokemon, true)
        }

    fun isPokemonListAttributesEmpty(): Boolean =
        pokemonList.isEmpty()

    fun getListPokemonAttributes(): ListPokemonAttributes =
        getListPokemonAttributes(pokemonList)

    fun getPokemonDetail(namePokemon: String): PokemonInfo? =
        when (pokemonDetail?.base?.name == namePokemon) {
            true -> pokemonDetail
            false -> null
        }

    private fun onServiceFinishedWork(pokemonAnswer: ServicePokemonAnswer) {
        when (pokemonAnswer) {
            is ListPokemon -> {
                pokemonList = pokemonAnswer.listPokemon
                onListReadyListener?.invoke(getListPokemonAttributes(pokemonList))
            }
            is PokiInfo -> {
                getPokemonInfo(pokemonAnswer).let {
                    pokemonDetail = it
                    onListReadyListener?.invoke(it)
                }
            }
        }
    }

    private fun getListPokemonAttributes(listPokemon: List<Pokemon>): ListPokemonAttributes {
        val listAttributes: MutableList<PokemonAttributes> = mutableListOf()
        listPokemon.forEach {
            listAttributes.add(PokemonAttributes(it.sprites.frontDefault, firstUpperCase(it.name)))
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
            firstUpperCase(pokemon?.name),
            pokemon?.baseExperience ?: 0,
            pokiInfo.pokemonSpecies.captureRate,
            pokemon?.height?.times(10) ?: 0,
            pokemon?.weight?.div(100.0) ?: 0.00,
            when (pokiInfo.pokemonSpecies.isBaby) {
                true -> R.string.baby
                false -> R.string.adult
            },
            firstUpperCase(pokiInfo.pokemonSpecies.habitat.name),
            firstUpperCase(pokiInfo.pokemonSpecies.color.name)
        )


    private fun takeAbilitiesInfo(abilities: List<Ability>): List<PokiAbility> {
        val abilitiesInfo: MutableList<PokiAbility> = mutableListOf()
        abilities.forEach { ability ->
            ability.effectEntries.firstOrNull {
                it.language.name == ENGLISH_LANGUAGE
            }?.let {
                abilitiesInfo.add(
                    PokiAbility(
                        firstUpperCase(ability.name), it.effect.replace(TWO_NL, PARAGRAPH)
                    )
                )
            }
        }
        return abilitiesInfo
    }

    private fun takeTypesInfo(pokiInfo: PokiInfo): List<PokiType> {
        val typesInfo: MutableList<PokiType> = mutableListOf()
        pokiInfo.types.forEach { type ->
            typesInfo.add(
                PokiType(
                    firstUpperCase(type.name),
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

    private fun firstUpperCase(word: String?): String {
        if (word.isNullOrBlank()) {
            return ""
        }
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
    }

    companion object {
        private const val ENGLISH_LANGUAGE = "en"
        private const val TWO_NL = "\n\n"
        private const val PARAGRAPH = "\n\t\t\t"
    }
}