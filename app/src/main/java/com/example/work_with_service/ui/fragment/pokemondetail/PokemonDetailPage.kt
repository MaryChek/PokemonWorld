package com.example.work_with_service.ui.fragment.pokemondetail

import android.os.Bundle
import android.text.*
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.example.work_with_service.App
import com.example.work_with_service.R
import android.text.Spanned.*
import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.databinding.ItemPokemonAdditionalInfoBinding
import com.example.work_with_service.databinding.ItemPokemonBaseInfoBinding
import com.example.work_with_service.databinding.PokemonBaseInformationBinding
import com.example.work_with_service.databinding.PokemonDetailPageBinding
import com.example.work_with_service.ui.model.*
import com.example.work_with_service.ui.pager.adapter.PagerAdapter.Companion.KEY_FOR_NAME_POKEMON_ARG
import com.example.work_with_service.ui.presenter.PokemonDetailsPresenter
import com.example.work_with_service.ui.utils.firstUpperCase
import com.example.work_with_service.ui.utils.setImageWithGlide

class PokemonDetailPage : Fragment(), PokemonDetailsContract.View {
    private var binding: PokemonDetailPageBinding? = null
    private var presenter: PokemonDetailsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val model: PokemonModel = (requireActivity().applicationContext as App).pokemonModel
        presenter = PokemonDetailsPresenter(model, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PokemonDetailPageBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            bundle.get(KEY_FOR_NAME_POKEMON_ARG)?.let {
                presenter?.onViewGetPokemonAttributes(it as PokemonAttributes)
            }
        }
    }

    override fun showDetail(pokemonDetail: PokemonDetail) {
        binding?.let {
            setImageWithGlide(it.ivPokemon.rootView, pokemonDetail.imageUrl, it.ivPokemon)
            setBaseInfo(it.cvBaseInformation, pokemonDetail.base)
            setInfoByType(it.cvTypes, pokemonDetail.types)
            setInfoByAbilities(it.cvAbilities, pokemonDetail.abilities)
        }
    }

    private fun setBaseInfo(baseInfoBinding: PokemonBaseInformationBinding, baseInfo: BaseInfo) {
        binding?.tvNamePokemon?.text = firstUpperCase(baseInfo.name)
        setTextToItemBaseInfo(
            baseInfoBinding.tvBasExperience,
            R.string.base_experience,
            baseInfo.baseExperience.toString()
        )
        var text: String = resources.getString(R.string.capture_rate_value, baseInfo.captureRate)
        setTextToItemBaseInfo(baseInfoBinding.tvCaptureRate, R.string.capture_rate, text)
        text = resources.getString(R.string.pokemon_height_value, baseInfo.height.times(10))
        setTextToItemBaseInfo(baseInfoBinding.tvHeight, R.string.pokemon_height, text)
        text = resources.getString(R.string.pokemon_weight_value, baseInfo.weight.div(100.0))
        setTextToItemBaseInfo(baseInfoBinding.tvWeight, R.string.pokemon_weight, text)
        text = when (baseInfo.isBaby) {
            true -> resources.getString(R.string.baby)
            false -> resources.getString(R.string.adult)
        }
        setTextToItemBaseInfo(baseInfoBinding.tvAgeCategory, R.string.age_category, text)
        setTextToItemBaseInfo(
            baseInfoBinding.tvHabitat, R.string.habitat, firstUpperCase(baseInfo.habitat)
        )
        setTextToItemBaseInfo(
            baseInfoBinding.tvColor, R.string.color, firstUpperCase(baseInfo.color)
        )
    }

    private fun setTextToItemBaseInfo(
        item: ItemPokemonBaseInfoBinding,
        @StringRes textHeadId: Int,
        valueText: String
    ) {
        item.tvHeading.text = resources.getString(textHeadId)
        item.tvValue.text = valueText
    }

    private fun setInfoByAbilities(
        pokemonAbilityBinding: ItemPokemonAdditionalInfoBinding,
        abilities: List<PokiAbility>
    ) {
        val properties = SpannableStringBuilder("")
        for (i in abilities.indices) {
            var text = "\t" + resources.getString(
                R.string.name_ability, i + 1, firstUpperCase(abilities[i].name)
            )
            properties.append(getSpannableStringWithStyle(text, R.style.TextView_Heading))
            text = PARAGRAPH + abilities[i].effect.replace("\n\n",
                PARAGRAPH
            ) + "\n"
            properties.append(getSpannableStringWithStyle(text, R.style.TextView_Normal))
        }
        setTextToItemAdditionalInfo(pokemonAbilityBinding, R.string.abilities, properties)
    }

    private fun setTextToItemAdditionalInfo(
        item: ItemPokemonAdditionalInfoBinding,
        @StringRes textHeadId: Int,
        valueText: SpannableStringBuilder
    ) {
        item.tvHeading.text = resources.getString(textHeadId)
        item.tvValue.text = valueText
    }

    private fun getSpannableStringWithStyle(
        text: String,
        @StyleRes styleRes: Int
    ): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(
            TextAppearanceSpan(activity, styleRes), 0, text.length, SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    private fun setInfoByType(
        pokemonTypeBinding: ItemPokemonAdditionalInfoBinding,
        typeInfo: List<PokiType>
    ) {
        val types = SpannableStringBuilder("")
        for (i in typeInfo.indices) {
            val text = "\t" + resources.getString(
                R.string.name_type, i + 1, firstUpperCase(typeInfo[i].name)
            )
            types.append(getSpannableStringWithStyle(text, R.style.TextView_Heading))
            types.append(getTextTypeNamesByDamage(typeInfo[i].noDamageTo, R.string.no_damage_to))
            types.append(
                getTextTypeNamesByDamage(typeInfo[i].doubleDamageTo, R.string.double_damage_to)
            )
            types.append(
                getTextTypeNamesByDamage(typeInfo[i].noDamageFrom, R.string.no_damage_from)
            )
            types.append(
                getTextTypeNamesByDamage(typeInfo[i].doubleDamageFrom, R.string.double_damage_from)
            )
            types.append("\n")
        }
        setTextToItemAdditionalInfo(pokemonTypeBinding, R.string.types, types)
    }

    private fun getTextTypeNamesByDamage(
        typeNames: List<String>,
        @StringRes nameTypeId: Int
    ): SpannableStringBuilder {
        val text = SpannableStringBuilder("")
        if (typeNames.isNotEmpty()) {
            var textTypeNames: String = PARAGRAPH + resources.getString(nameTypeId)
            text.append(getSpannableStringWithStyle(textTypeNames, R.style.TextView_Heading))
            textTypeNames = typeNames.joinToString(separator = ", ")
            text.append(getSpannableStringWithStyle(textTypeNames, R.style.TextView_Normal))
        }
        return text
    }

    companion object {
        private const val PARAGRAPH = "\n\t\t\t"
    }
}
