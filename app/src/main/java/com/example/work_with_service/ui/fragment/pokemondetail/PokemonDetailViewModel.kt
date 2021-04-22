package com.example.work_with_service.ui.fragment.pokemondetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.data.model.Ability
//import com.example.work_with_service.data.model.NameResource
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.ui.model.*

class PokemonDetailViewModel : ViewModel() {
//    private var pokemonAttributes: PokemonAttributes? = null
//
//    val pokemonDetailLive = MutableLiveData<PokemonDetail>()
//
//    fun fetchPokemonDetail(pokemonAttributes: PokemonAttributes) {
//        this.pokemonAttributes = pokemonAttributes
//        PokemonRepository.getInstance().callPokemonInfo(
//            pokemonAttributes.name, this::onPokemonDetailReady
//        )
//    }
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