package com.example.work_with_service.ui.model

import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.data.entities.Ability
import com.example.work_with_service.data.entities.NameResource
import com.example.work_with_service.data.entities.Pokemon

class PokemonModel {
    private var onListReadyListener: ((PokiAttributes) -> Unit)? = null
    private var pokemonRepository: PokemonRepository? = null
    private var pokemonList: List<Pokemon> = listOf()
    private var pokemon: Pokemon? = null

    init {
        pokemonRepository =
            PokemonRepository(this::onServiceFinishedWork)
    }

    fun createPokemonList(onPokemonListReadyListener: (PokiAttributes) -> Unit) {
        onListReadyListener = onPokemonListReadyListener
        pokemonRepository?.callPokemonSource()
    }

    fun createPokemonInfo(
        namePokemon: String,
        onPokemonInfoReadyListener: (PokiAttributes) -> Unit
    ) {
        onListReadyListener = onPokemonInfoReadyListener
        pokemon = getPokemonByName(namePokemon)
        pokemon?.let {
            pokemonRepository?.callPokemonInfo(it)
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

    private fun onServiceFinishedWork(pokemonAnswer: ServicePokemonAnswer) {
        when (pokemonAnswer) {
            is ListPokemon -> {
                pokemonList = pokemonAnswer.listPokemon
                onListReadyListener?.invoke(getListPokemonAttributes(pokemonList))
            }
            is PokiDetail ->
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

    private fun getPokemonInfo(pokiDetail: PokiDetail): PokemonDetail =
        PokemonDetail(
            pokemon?.sprites?.frontDefault,
            takeBaseInfo(pokiDetail),
            takeAbilitiesInfo(pokiDetail.abilities),
            takeTypesInfo(pokiDetail)
        )

    private fun takeBaseInfo(pokiDetail: PokiDetail): BaseInfo =
        BaseInfo(
            pokemon?.name ?: "",
            pokemon?.baseExperience ?: 0,
            pokiDetail.pokemonSpecies.captureRate,
            pokemon?.height ?: 0,
            pokemon?.weight ?: 0,
            pokiDetail.pokemonSpecies.isBaby,
            pokiDetail.pokemonSpecies.habitat.name,
            pokiDetail.pokemonSpecies.color.name
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

    private fun takeTypesInfo(pokiDetail: PokiDetail): List<PokiType> {
        val typesInfo: MutableList<PokiType> = mutableListOf()
        pokiDetail.types.forEach { type ->
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