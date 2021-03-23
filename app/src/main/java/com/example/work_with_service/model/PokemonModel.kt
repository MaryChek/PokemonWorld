package com.example.work_with_service.model

class PokemonModel {
    private var onListReadyListener: ((PokiAttributes) -> Unit)? = null
    private var pokemonService: PokemonService? = null
    private var pokemonList: List<Pokemon> = listOf()
    private var pokemon: Pokemon? = null

    init {
        pokemonService = PokemonService(this::onServiceFinishedWork)
    }

    fun createPokemonList(onPokemonListReadyListener: (PokiAttributes) -> Unit) {
        onListReadyListener = onPokemonListReadyListener
        pokemonService?.callPokemonSource()
    }

    fun createPokemonInfo(
        namePokemon: String,
        onPokemonInfoReadyListener: (PokiAttributes) -> Unit
    ) {
        onListReadyListener = onPokemonInfoReadyListener
        getPokemonByName(namePokemon)
        pokemonService?.callPokemonInfo(pokemon!!)
    }

    private fun getPokemonByName(namePokemon: String) {
        pokemon = pokemonList.first {
            it.name == namePokemon
        }
    }

    fun isPokemonListAttributesEmpty() : Boolean =
        pokemonList.isEmpty()

    fun getListPokemonAttributes() : ListPokemonAttributes =
        getListPokemonAttributes(pokemonList)

    private fun onServiceFinishedWork(pokemonAnswer: ServicePokemonAnswer) {
        when (pokemonAnswer) {
            is ListPokemon -> {
                pokemonList = pokemonAnswer.listPokemon
                onListReadyListener?.invoke(getListPokemonAttributes(pokemonList))
            }
            is PokiInfo -> {
                onListReadyListener?.invoke(getPokemonInfo(pokemonAnswer))
            }
        }

    }

    private fun getListPokemonAttributes(listPokemon: List<Pokemon>): ListPokemonAttributes {
        val listAttributes: MutableList<PokemonAttributes> = mutableListOf()
        listPokemon.forEach {
            listAttributes.add(
                PokemonAttributes(
                    it.sprites.frontDefault,
                    it.name
                )
            )
        }
        return ListPokemonAttributes(
            listAttributes
        )
    }

    private fun getPokemonInfo(pokiInfo: PokiInfo): PokemonInfo {
        return PokemonInfo(
            pokemon?.sprites?.frontDefault!!,
            takeBaseInfo(pokiInfo),
            takeAbilitiesInfo(pokiInfo),
            pokiInfo.pokemonSpecies.captureRate,
            takeTypesInfo(pokiInfo)
        )
    }

    private fun takeBaseInfo(pokiInfo: PokiInfo): Base {
        pokemon?.let {
            return Base(
                it.name,
                it.baseExperience,
                it.height,
                it.weight,
                pokiInfo.pokemonSpecies.isBaby,
                pokiInfo.pokemonSpecies.habitat.name,
                pokiInfo.pokemonSpecies.color.name
            )
        }
        return Base()
    }


    private fun takeAbilitiesInfo(pokiInfo: PokiInfo): List<PokiAbility> {
        val abilitiesInfo: MutableList<PokiAbility> = mutableListOf()
        pokiInfo.ability.forEach { ability ->
            ability.effectEntries.firstOrNull {
                it.language.name == ENGLISH_LANUAGE
            }?.let {
                abilitiesInfo.add(
                    PokiAbility(
                        ability.name,
                        it.effect,
                        it.shortEffect
                    )
                )
            }
        }
        return abilitiesInfo
    }

    private fun takeTypesInfo(pokiInfo: PokiInfo): List<PokiType> {
        val typesInfo: MutableList<PokiType> = mutableListOf()
        pokiInfo.type.forEach { type ->
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

    private fun takeDamageFrom(damageApiResource: List<NamedApiResource>): List<String> {
        val damage: MutableList<String> = mutableListOf()
        damageApiResource.forEach {
            damage.add(it.name)
        }
        return damage
    }

    companion object {
        private const val ENGLISH_LANUAGE = "en"
    }
}

