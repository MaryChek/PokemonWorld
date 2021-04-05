package com.example.work_with_service.ui.fragment

import android.os.Bundle
import android.text.*
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View.*
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.example.work_with_service.App
import com.example.work_with_service.R
import android.text.Spanned.*
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.databinding.FragmentPokemonDetailPageBinding
import com.example.work_with_service.databinding.ItemPokemonAdditionalInfoBinding
import com.example.work_with_service.databinding.ItemPokemonBaseInfoBinding
import com.example.work_with_service.databinding.PokemonBaseInformationBinding
import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.*
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail.Ability
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail.Type
import com.example.work_with_service.ui.presenter.PokemonDetailsPresenter
import com.example.work_with_service.ui.utils.setImageWithGlide

class PokemonDetailPageFragment : Fragment(), PokemonDetailsContract.View {
    private var binding: FragmentPokemonDetailPageBinding? = null
    private lateinit var presenter: PokemonDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val model: PokemonDetailModel =
            (requireActivity().applicationContext as App).pokemonDetailModel
        presenter = PokemonDetailsPresenter(model, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailPageBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPokemon()
    }

    private fun initPokemon() {
        arguments?.let { bundle ->
            bundle.getSerializable("namePokemon")?.let {
                presenter.onViewGetPokemonName(it as Pokemon)
            }
        }
    }

    override fun showLoadingIndicator() {
        binding?.progressIndicator?.visibility = VISIBLE
    }

    override fun hideLoadingIndicator() {
        binding?.progressIndicator?.visibility = GONE
    }

    override fun showConnectionErrorMessage() {
        binding?.connectionError?.root?.visibility = VISIBLE
        setOnRetryConnectionClickListener()
    }

    private fun setOnRetryConnectionClickListener() {
        binding?.connectionError?.buttonRetryConnection?.setOnClickListener {
            presenter.onRetryConnectionClick()
        }
    }

    override fun hideConnectionErrorMessage() {
        binding?.connectionError?.root?.visibility = GONE
    }

    override fun showDetail(pokemonDetail: PokemonDetail) {
        binding?.ivPokemon?.visibility = VISIBLE
        binding?.let {
            setImageWithGlide(it.ivPokemon.rootView, pokemonDetail.imageUrl, it.ivPokemon)
            setBaseInfo(it.cvBaseInformation, pokemonDetail)
            setInfoByType(it.cvTypes, pokemonDetail.types)
            setInfoByAbilities(it.cvAbilities, pokemonDetail.abilities)
        }
    }

    private fun setBaseInfo(baseInfoBinding: PokemonBaseInformationBinding, detail: PokemonDetail) {
        baseInfoBinding.root.visibility = VISIBLE
        binding?.tvNamePokemon?.text = detail.name
        setTextToItemBaseInfo(
            baseInfoBinding.tvBasExperience,
            R.string.base_experience,
            detail.baseExperience.toString()
        )
        var text: String = resources.getString(R.string.capture_rate_value, detail.captureRate)
        setTextToItemBaseInfo(baseInfoBinding.tvCaptureRate, R.string.capture_rate, text)
        text = resources.getString(R.string.pokemon_height_value, detail.height)
        setTextToItemBaseInfo(baseInfoBinding.tvHeight, R.string.pokemon_height, text)
        text = resources.getString(R.string.pokemon_weight_value, detail.weight)
        setTextToItemBaseInfo(baseInfoBinding.tvWeight, R.string.pokemon_weight, text)
        text = resources.getString(detail.ageId)
        setTextToItemBaseInfo(baseInfoBinding.tvAgeCategory, R.string.age_category, text)
        setTextToItemBaseInfo(baseInfoBinding.tvHabitat, R.string.habitat, detail.habitat)
        setTextToItemBaseInfo(baseInfoBinding.tvColor, R.string.color, detail.color)
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
        abilities: List<Ability>
    ) {
        pokemonAbilityBinding.root.visibility = VISIBLE
        val properties = SpannableStringBuilder("")
        var text: String
        for (i in abilities.indices) {
            text = "\t" + resources.getString(R.string.name_ability, (i + 1), abilities[i].name)
            properties.append(getSpannableStringWithStyle(text, R.style.TextView_Heading))
            text = PARAGRAPH + abilities[i].effect + "\n"
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

    private fun getSpannableStringWithStyle(text: String, @StyleRes styleRes: Int)
            : SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(
            TextAppearanceSpan(activity, styleRes), (0), text.length, SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    private fun setInfoByType(
        pokemonTypeBinding: ItemPokemonAdditionalInfoBinding,
        typeInfo: List<Type>
    ) {
        pokemonTypeBinding.root.visibility = VISIBLE
        val types = SpannableStringBuilder("")
        var text: String
        for (i in typeInfo.indices) {
            text = "\t" + resources.getString(R.string.name_type, (i + 1), typeInfo[i].name)
            types.append(getSpannableStringWithStyle(text, R.style.TextView_Heading))
            types.append(getTypeNamesByDamage(typeInfo[i].noDamageTo, R.string.no_damage_to))
            types.append(
                getTypeNamesByDamage(typeInfo[i].doubleDamageTo, R.string.double_damage_to)
            )
            types.append(getTypeNamesByDamage(typeInfo[i].noDamageFrom, R.string.no_damage_from))
            types.append(
                getTypeNamesByDamage(typeInfo[i].doubleDamageFrom, R.string.double_damage_from)
            )
            types.append("\n")
        }
        setTextToItemAdditionalInfo(pokemonTypeBinding, R.string.types, types)
    }

    private fun getTypeNamesByDamage(typeNames: List<String>, @StringRes nameTypeId: Int)
            : SpannableStringBuilder {
        val textTypeNames = SpannableStringBuilder("")
        var text: String
        if (typeNames.isNotEmpty()) {
            text = PARAGRAPH + resources.getString(nameTypeId)
            textTypeNames.append(getSpannableStringWithStyle(text, R.style.TextView_Heading))
            text = typeNames.joinToString(separator = ", ")
            textTypeNames.append(getSpannableStringWithStyle(text, R.style.TextView_Normal))
        }
        return textTypeNames
    }

    companion object {
        private const val PARAGRAPH = "\n\t\t\t"
    }
}
