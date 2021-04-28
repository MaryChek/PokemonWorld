package com.example.work_with_service.ui.fragment.pokemondetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.App
import com.example.work_with_service.R
import com.example.work_with_service.databinding.*
import com.example.work_with_service.ui.activity.MainActivity
import com.example.work_with_service.ui.adapter.PokemonAbilitiesAdapter
import com.example.work_with_service.ui.adapter.PokemonTypesAdapter
import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.PokemonDetail
import com.example.work_with_service.ui.model.PokemonDetail.Ability
import com.example.work_with_service.ui.model.PokemonDetail.Type
import com.example.work_with_service.ui.model.PokemonDetailModel
import com.example.work_with_service.ui.presenter.PokemonDetailsPresenter
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*

class PokemonDetailPageFragment : Fragment(), PokemonDetailsContract.View {
    private var binding: FragmentPokemonDetailBinding? = null
    private lateinit var presenter: PokemonDetailsPresenter
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        flag = true
    }

    private fun init() {
        val app: App = (requireActivity().applicationContext as App)
        val model: PokemonDetailModel = app.pokemonDetailModel
        presenter = PokemonDetailsPresenter(model, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigationToolBar()
        initPokemon()
    }

    private fun initNavigationToolBar() {
        binding?.let {
//            (requireActivity() as MainActivity).supportActionBar?.hide()
            val navController: NavController = findNavController()
            val appBarConfiguration = AppBarConfiguration(navController.graph)
//            (activity as MainActivity).setSupportActionBar(it.toolbar)
//            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.collapsingToolbar.setupWithNavController(
                it.toolbar,
                navController,
                appBarConfiguration
            )

//             .setDisplayHomeAsUpEnabled(true)
//            val appBarConfiguration = AppBarConfiguration(navController.graph)
//            it.toolbar.setupWithNavController(navController, appBarConfiguration)
//            NavigationUI.setupWithNavController(it.toolbar, navController, appBarConfiguration)
//            NavigationUI.setupWithNavController(it.collapsingToolbar, it.toolbar, navController)

        }
    }

    private fun initPokemon() {
        arguments?.let { bundle ->
            bundle.getSerializable(KEY_FOR_POKEMON_ARG)?.let {
                presenter.init(it as Pokemon)
            }
        }
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

    override fun showPokemonMain(detail: PokemonDetail, pokemon: Pokemon) {
        binding?.let {
            it.toolbar.title = detail.name
            it.ivPokemon.load(pokemon.imageUrl) {
                placeholder(R.mipmap.ic_pokeball_foreground)
            }
            it.cvPokemonMain.apply {
                root.visibility = VISIBLE
                tvBasExperience.setTextToItems(
                    R.string.base_experience, pokemon.baseExperience.toString()
                )
                var text: String =
                    resources.getString(R.string.capture_rate_value, detail.captureRate)
                tvCaptureRate.setTextToItems(R.string.capture_rate, text)
                text = resources.getString(R.string.pokemon_height_value, pokemon.height)
                tvHeight.setTextToItems(R.string.pokemon_height, text)
                text = resources.getString(R.string.pokemon_weight_value, pokemon.weight)
                tvWeight.setTextToItems(R.string.pokemon_weight, text)
                text = resources.getString(detail.resIdAge)
                tvAgeCategory.setTextToItems(R.string.age_category, text)
                tvHabitat.setTextToItems(R.string.habitat, detail.habitat)
                tvColor.setTextToItems(R.string.color, detail.color)
            }
        }
    }

    private fun ItemPokemonBaseBinding.setTextToItems(
        @StringRes textHeadId: Int,
        valueText: String
    ) {
        tvHeading.text = resources.getString(textHeadId)
        tvValue.text = valueText
    }

    override fun showPokemonAbilities(abilities: List<Ability>) {
        binding?.cvAbilities?.apply {
            root.visibility = VISIBLE
            rvPokemonAbilities.adapter = PokemonAbilitiesAdapter(abilities)
        }
    }

    override fun showPokemonTypes(types: List<Type>) {
        binding?.cvTypes?.apply {
            root.visibility = VISIBLE
            rvPokemonTypes.adapter = PokemonTypesAdapter(types)
        }
    }

    override fun onDetach() {
        super.onDetach()
        flag = false
    }

    companion object {
        private const val KEY_FOR_POKEMON_ARG = "namePokemon"

//        fun newInstance(pokemon: Pokemon?) = PokemonDetailPageFragment().apply {
//            pokemon.let {
//                arguments = bundleOf(KEY_FOR_NAME_POKEMON_ARG to it)
//            }
//        }
    }
}
