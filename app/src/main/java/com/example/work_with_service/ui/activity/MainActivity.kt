package com.example.work_with_service.ui.activity

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ActivityMainBinding
import com.example.work_with_service.ui.fragment.pager.PagerPokemonFragment
import com.example.work_with_service.ui.fragment.pokemondetail.DetailPage
import com.example.work_with_service.ui.fragment.pokemondetail.PokemonDetailPageFragment
import com.example.work_with_service.ui.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), DetailPage {
    private var binding: ActivityMainBinding? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        setSupportActionBar(binding?.toolbar)
        initNavigation()
//        attachRootFragment(savedInstanceState)
    }

    private fun initNavigation() {
        val navHost: NavHost =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
        navController = navHost.navController
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean =
//        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)


    override fun onSupportNavigateUp(): Boolean {
        supportActionBar?.show()
        return navController.navigateUp() // app.. <-
    }

    override fun openDetailedPage(pokemon: Pokemon) {
        val arguments: Bundle = bundleOf(KEY_FOR_POKEMON_ARG to pokemon)
        navController.navigate(R.id.pokemonDetailPageFragment, arguments)
    }

//    private fun attachRootFragment(savedInstanceState: Bundle?) {
//        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
//        if (savedInstanceState == null) {
//            val pagerPokemon = PagerPokemonFragment()
//            fragmentTransition.replace(R.id.mainActivity, pagerPokemon, null).commit()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val KEY_FOR_POKEMON_ARG = "namePokemon"
    }
}