package com.example.work_with_service.presentation.viewmodels

import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper
import com.example.work_with_service.domain.models.PokemonDetail as DomainPokemonDetail
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonDetail
import com.example.work_with_service.presentation.models.State
import com.example.work_with_service.presentation.models.PokemonDetailModel
import com.example.work_with_service.presentation.navigation.FromPokemonDetail
import com.example.work_with_service.presentation.viewmodels.base.BaseRefreshableViewModel

class PokemonDetailViewModel(
    private val mapper: PokemonDetailMapper,
    private val interactor: PokemonInteractor
) : BaseRefreshableViewModel<PokemonDetailModel, FromPokemonDetail>(PokemonDetailModel(), mapper) {

    private fun updateModel(
        pokemon: Pokemon? = model.pokemon,
        namePokemon: String? = model.name,
        imageUrl: String? = model.imageUrl,
        baseExperience: String? = model.baseExperience,
        pokemonHeight: String? = model.pokemonHeight,
        pokemonWeight: Double? = model.pokemonWeight,
        state: State = model.resource,
        pokemonDetail: PokemonDetail? = model.pokemonDetail,
    ) {
        model = PokemonDetailModel(
            pokemon, namePokemon, imageUrl, baseExperience, pokemonHeight, pokemonWeight,
            pokemonDetail, state,
        )
        updateScreen()
    }

    fun init(pokemon: Pokemon) =
        updateModel(
            namePokemon = pokemon.name, imageUrl = pokemon.imageUrl,
            baseExperience = pokemon.baseExperience.toString(),
            pokemonHeight = pokemon.height.toString(), pokemonWeight = pokemon.weight
        )

    fun fetchPokemonDetail() {
        model.pokemon?.let { pokemon ->
            mapper.mapToDomainPokemon(pokemon).let { domainPokemon ->
                interactor.fetchPokemonDetail(
                    domainPokemon.name,
                    domainPokemon.abilityNames,
                    domainPokemon.typeNames,
                    this::onPokemonDetailGet
                )
            }
        }
    }

    private fun onPokemonDetailGet(resource: Resource<DomainPokemonDetail>) {
        val state: State = getResourceState(resource)
        val pokemonDetail: PokemonDetail? = resource.data?.let { detail ->
            mapper.map(detail)
        }
        updateModel(state = state, pokemonDetail = pokemonDetail)
    }

    override fun goToPrevious() =
        goToScreen(FromPokemonDetail.PreviousScreen)

    override fun onButtonRetryConnectionClick() {

    }
//    private fun onPokemonDetailReady(pokiDetail: ServicePokemonAnswer) {
//        pokemonDetailLive.value = getPokemonDetail(pokiDetail as PokiDetail)
//    }
//
//    private fun getPokemonDetail(pokiDetail: PokiDetail): PokemonDetail =
//        PokemonDetail(
//            pokemonAttributes?.imageUrl,
//            takeBaseInfo(pokiDetail),
//            takeAbilitiesInfo(pokiDetail.abilities),
//            takeTypesInfo(pokiDetail)
//        )
//
//    private fun takeBaseInfo(pokiDetail: PokiDetail): BaseInfo =
//        BaseInfo(
//            pokiDetail.name,
//            pokiDetail.baseExperience,
//            pokiDetail.pokemonSpecies.captureRate,
//            pokiDetail.height,
//            pokiDetail.weight,
//            pokiDetail.pokemonSpecies.isBaby,
//            pokiDetail.pokemonSpecies.habitat.name,
//            pokiDetail.pokemonSpecies.color.name
//        )
//
//
//    private fun takeAbilitiesInfo(abilities: List<Ability>): List<PokiAbility> {
//        val abilitiesInfo: MutableList<PokiAbility> = mutableListOf()
//        abilities.forEach { ability ->
//            ability.effectEntries.firstOrNull {
//                it.language.name == ENGLISH_LANGUAGE
//            }?.let {
//                abilitiesInfo.add(PokiAbility(ability.name, it.effect))
//            }
//        }
//        return abilitiesInfo
//    }
//
//    private fun takeTypesInfo(pokiDetail: PokiDetail): List<PokiType> {
//        val typesInfo: MutableList<PokiType> = mutableListOf()
//        pokiDetail.types.forEach { type ->
//            typesInfo.add(
//                PokiType(
//                    type.name,
//                    takeDamageFrom(type.damageRelations.noDamageTo),
//                    takeDamageFrom(type.damageRelations.doubleDamageTo),
//                    takeDamageFrom(type.damageRelations.noDamageFrom),
//                    takeDamageFrom(type.damageRelations.doubleDamageFrom)
//                )
//            )
//        }
//        return typesInfo
//    }
//
//    private fun takeDamageFrom(damageApiResource: List<NameResource>): List<String> {
//        val damage: MutableList<String> = mutableListOf()
//        damageApiResource.forEach {
//            damage.add(it.name)
//        }
//        return damage
//    }
//
//    companion object {
//        private const val ENGLISH_LANGUAGE = "en"
//    }
}