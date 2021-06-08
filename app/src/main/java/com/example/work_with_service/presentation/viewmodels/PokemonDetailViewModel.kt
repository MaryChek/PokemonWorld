package com.example.work_with_service.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.models.Status
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper
import com.example.work_with_service.domain.models.PokemonDetail as DomainPokemonDetail
import com.example.work_with_service.presentation.models.*

class PokemonDetailViewModel(
    private val mapper: PokemonDetailMapper,
    private val interactor: PokemonInteractor
) : ViewModel() {

    val pokemonDetail = MutableLiveData<Resource<PokemonDetail>>()
    val pokemon = Pokemon

    fun fetchPokemonDetail(pokemon: Pokemon) {
        this.pokemon.value = pokemon
//        pokemonDetail.value = Resource.loading()
        mapper.map(pokemon).let {
            interactor.fetchPokemonDetail(
                it.name,
                it.abilityNames,
                it.typeNames,
                this::onServiceFinishedWork
            )
        }
    }

    private fun onServiceFinishedWork(pokemonDetail: Resource<DomainPokemonDetail>) {
        this.pokemonDetail.value =
            if (pokemonDetail.data != null && pokemonDetail.status == Status.SUCCESS) {
                Resource.success(mapper.map(pokemonDetail.data))
            } else {
                pokemonDetail.message?.let {
                    Log.w(null, it)
                }
                Resource.error((pokemonDetail.message ?: ""), null)
            }
    }
//
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