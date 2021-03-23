package com.example.work_with_service.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.*
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.work_with_service.App
import com.example.work_with_service.R
import com.example.work_with_service.contract.PokemonDetailsContract
import com.example.work_with_service.databinding.ItemPokemonBaseInfoBinding
import com.example.work_with_service.databinding.PokemonBaseInformationBinding
import com.example.work_with_service.databinding.PokemonDetailPageBinding
import com.example.work_with_service.model.Base
import com.example.work_with_service.model.PokemonInfo
import com.example.work_with_service.model.PokemonModel
import com.example.work_with_service.model.PokiAbility
import com.example.work_with_service.presenter.PokemonDetailsPresenter
import java.util.*

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
            bundle.getString("namePokemon")?.let {
                presenter?.onViewGetPokemonName(it)
            }
        }
    }

    override fun showDetail(pokemonInfo: PokemonInfo) {
        binding?.cvBaseInformation?.let {
            setBaseInfo(it, pokemonInfo.base)
        }
        setAbilityInfo(pokemonInfo.abilities)
        Glide.with(binding?.ivPokemon?.rootView!!)
            .asBitmap()
            .load(pokemonInfo.imageUrl)
            .transform(CircleCrop())
            .placeholder(R.mipmap.ic_launcher_foreground)
            .into(binding?.ivPokemon!!)
    }

    @SuppressLint("SetTextI18n")
    private fun setBaseInfo(baseInfoBinding: PokemonBaseInformationBinding, baseInfo: Base) {
        binding?.tvNamePokemon?.text = firstUpperCase(baseInfo.name)
        setTextToItemBaseInfo(
            baseInfoBinding.tvBasExperience,
            R.string.base_experience,
            baseInfo.baseExperience.toString()
        )
        var text: String = resources.getString(
            R.string.pokemon_height_value,
            baseInfo.height.times(10)
        )
        setTextToItemBaseInfo(baseInfoBinding.tvHeight, R.string.pokemon_height, text)
        text = resources.getString(
            R.string.pokemon_weight_value,
            baseInfo.weight.div(100.0)
        )
        setTextToItemBaseInfo(baseInfoBinding.tvWeight, R.string.pokemon_weight, text)
        text =
            if (baseInfo.isBaby) {
                resources.getString(R.string.baby)
            } else {
                resources.getString(R.string.adult)
            }
        setTextToItemBaseInfo(baseInfoBinding.tvAgeCategory, R.string.age_category, text)
        setTextToItemBaseInfo(
            baseInfoBinding.tvHabitat,
            R.string.habitat,
            firstUpperCase(baseInfo.habitat)
        )
        setTextToItemBaseInfo(
            baseInfoBinding.tvColor,
            R.string.color,
            firstUpperCase(baseInfo.color)
        )


//        setTextToHead(baseInfoBinding.tvBasExperience, R.string.base_experience)
//        setTextToValue(baseInfoBinding.tvBasExperience, baseInfo.baseExperience.toString())
//        setTextToHead(baseInfoBinding.tvHeight, R.string.pokemon_height)
//        var text: String = resources.getString(
//            R.string.pokemon_height_value,
//            baseInfo.height?.times(10)
//        )
//        setTextToValue(baseInfoBinding.tvHeight, text)
//        setTextToHead(baseInfoBinding.tvWeight, R.string.pokemon_weight)
//        text = resources.getString(
//            R.string.pokemon_weight_value,
//            baseInfo.weight?.div(100.0)
//        )
//        setTextToValue(baseInfoBinding.tvWeight, text)
//        setTextToHead(baseInfoBinding.tvAgeCategory, R.string.age_category)
//        text =
//            if (baseInfo.isBaby!!) {
//                resources.getString(R.string.baby)
//            } else {
//                resources.getString(R.string.adult)
//            }
//        setTextToValue(baseInfoBinding.tvAgeCategory, text)
//        setTextToHead(baseInfoBinding.tvHabitat, R.string.habitat)
//        setTextToValue(baseInfoBinding.tvHabitat, firstUpperCase(baseInfo.habitat))
//        setTextToHead(baseInfoBinding.tvColor, R.string.color)
//        setTextToValue(baseInfoBinding.tvColor, firstUpperCase(baseInfo.color))


//        baseInfoBinding.tvBasExperience.tvHeading.text =
//            resources.getString(R.string.base_experience)
//        baseInfoBinding.tvHeight.tvHeading.text = resources.getString(R.string.pokemon_height)
//        baseInfoBinding.tvBasExperience.tvValue.text = baseInfo.baseExperience.toString()
//        baseInfoBinding.tvHeight.tvValue.text = baseInfo.height?.times(10).toString() + "cm"


//        baseInfoBinding.tvWeight.tvHeading.text = resources.getString(R.string.pokemon_weight)
//        baseInfoBinding.tvWeight.tvValue.text = baseInfo.weight?.div(100.0).toString() + "kg"


//        baseInfoBinding.tvAgeCategory.tvHeading.text = resources.getString(R.string.age_category)
//        baseInfoBinding.tvAgeCategory.tvValue.text =
//            if (baseInfo.isBaby!!) {
//                resources.getString(R.string.baby)
//            } else {
//                resources.getString(R.string.adult)
//            }
//        baseInfoBinding.tvHabitat.tvHeading.text = resources.getString(R.string.habitat)
//        baseInfoBinding.tvHabitat.tvValue.text = firstUpperCase(baseInfo.habitat)
//        baseInfoBinding.tvColor.tvHeading.text = resources.getString(R.string.color)
//        baseInfoBinding.tvColor.tvValue.text = firstUpperCase(baseInfo.color)
    }

    private fun setTextToItemBaseInfo(
        item: ItemPokemonBaseInfoBinding,
        @StringRes textHeadId: Int,
        valueText: String
    ) {
        item.tvHeading.text = resources.getString(textHeadId)
        item.tvValue.text = valueText
    }

//    private fun setTextToHead(item: ItemPokemonBaseInfoBinding, @StringRes textId: Int) {
//        item.tvHeading.text = resources.getString(textId)
//    }
//
//    private fun setTextToValue(item: ItemPokemonBaseInfoBinding, text: String) {
//        item.tvValue.text = text
//    }

    private fun setAbilityInfo(abilityInfo: List<PokiAbility>) {
        val abilities = SpannableStringBuilder("")
        for (i in abilityInfo.indices) {
            var text = "\t" + resources.getString(
                R.string.name_ability,
                i + 1,
                firstUpperCase(abilityInfo[i].name)
            )
            abilities.append(getTextWithStyle(text, R.style.TextView_Heading))
            text = "\n\t\t\t" + resources.getString(R.string.ability_effect)
            abilities.append(getTextWithStyle(text, R.style.TextView_Heading))
            text = abilityInfo[i].effect.replace("\n\n", "\n\t") + "\n\t\t\t"
            abilities.append(getTextWithStyle(text, R.style.TextView_Normal))
            text = resources.getString(R.string.ability_short_effect)
            abilities.append(getTextWithStyle(text, R.style.TextView_Heading))
            text = abilityInfo[i].shortEffect + "\n"
            abilities.append(getTextWithStyle(text, R.style.TextView_Normal))
        }
        binding?.cvAbilities?.tvValue?.text = abilities
    }

    @SuppressLint("ResourceAsColor")
    private fun getTextWithStyle(text: String, @StyleRes styleRes: Int): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(
            TextAppearanceSpan(activity, styleRes),
            0, text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
}
