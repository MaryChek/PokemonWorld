package com.example.work_with_service.ui.fragment.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.ui.App
import com.example.work_with_service.R
import com.example.work_with_service.databinding.*
import com.example.work_with_service.ui.adapter.PokemonAbilitiesAdapter
import com.example.work_with_service.ui.adapter.PokemonTypesAdapter
import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.PokemonDetail
import com.example.work_with_service.ui.model.PokemonDetail.Ability
import com.example.work_with_service.ui.model.PokemonDetail.Type
import com.example.work_with_service.ui.model.PokemonDetailModel
import com.example.work_with_service.ui.model.PokemonListModel
import com.example.work_with_service.ui.presenter.PokemonDetailsPresenter

class PokemonDetailPageFragment : Fragment(), PokemonDetailsContract.View {
    private var binding: FragmentPokemonDetailBinding? = null
    private lateinit var presenter: PokemonDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val app: App = (requireActivity().applicationContext as App)
        val model: PokemonDetailModel = app.pokemonDetailModel
        presenter = PokemonDetailsPresenter(model, this)
    }

    private fun initPokemon() {
        arguments?.let { bundle ->
            bundle.getSerializable(KEY_FOR_NAME_POKEMON_ARG)?.let {
                presenter.init(it as Pokemon)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPokemon()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailBinding.inflate(inflater)
        return binding?.root
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
        binding?.let {
            it.ivPokemon.visibility = VISIBLE
            it.tvNamePokemon.text = pokemonDetail.name
            it.ivPokemon.load(pokemonDetail.imageUrl) {
                placeholder(R.mipmap.ic_pokeball_foreground)
            }
            setBaseInfo(it.cvBaseInformation, pokemonDetail)
            setInfoByType(it.cvTypes, pokemonDetail.types)
            setInfoByAbilities(it.cvAbilities, pokemonDetail.abilities)
        }
    }

    private fun setBaseInfo(baseInfoBinding: PokemonBaseInformationBinding, detail: PokemonDetail) =
        baseInfoBinding.apply {
            root.visibility = VISIBLE
            tvBasExperience.setTextToItems(
                R.string.base_experience, detail.baseExperience.toString()
            )
            var text: String = resources.getString(R.string.capture_rate_value, detail.captureRate)
            tvCaptureRate.setTextToItems(R.string.capture_rate, text)
            text = resources.getString(R.string.pokemon_height_value, detail.height)
            tvHeight.setTextToItems(R.string.pokemon_height, text)
            text = resources.getString(R.string.pokemon_weight_value, detail.weight)
            tvWeight.setTextToItems(R.string.pokemon_weight, text)
            text = resources.getString(detail.resIdAge)
            tvAgeCategory.setTextToItems(R.string.age_category, text)
            tvHabitat.setTextToItems(R.string.habitat, detail.habitat)
            tvColor.setTextToItems(R.string.color, detail.color)
        }

    private fun ItemPokemonBaseBinding.setTextToItems(
        @StringRes textHeadId: Int,
        valueText: String
    ) {
        tvHeading.text = resources.getString(textHeadId)
        tvValue.text = valueText
    }

    private fun setInfoByAbilities(
        pokemonAbilityBinding: ItemPokemonAbilitiesBinding,
        abilities: List<Ability>
    ) =
        pokemonAbilityBinding.apply {
            root.visibility = VISIBLE
            rvPokemonAbilities.adapter = PokemonAbilitiesAdapter(abilities)
        }

    private fun setInfoByType(pokemonTypeBinding: ItemPokemonTypesBinding, types: List<Type>) =
        pokemonTypeBinding.apply {
            root.visibility = VISIBLE
            rvPokemonTypes.adapter = PokemonTypesAdapter(types)
        }

    companion object {
        private const val KEY_FOR_NAME_POKEMON_ARG = "namePokemon"

        fun newInstance(pokemon: Pokemon?) = PokemonDetailPageFragment().apply {
            pokemon.let {
                arguments = bundleOf(KEY_FOR_NAME_POKEMON_ARG to it)
            }
        }
    }
}
