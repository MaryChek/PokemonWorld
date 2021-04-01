package com.example.work_with_service.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ActivityMainBinding
import com.example.work_with_service.ui.pager.PagerPokemonFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        attachRootFragment(savedInstanceState)
    }

    private fun attachRootFragment(savedInstanceState: Bundle?) {
        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (savedInstanceState == null) {
            val pagerPokemon = PagerPokemonFragment()
            fragmentTransition.replace(R.id.mainActivity, pagerPokemon, FRAGMENT_TAG).commit()
        } else {
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)?.let { pagerFragment ->
                fragmentTransition.attach(pagerFragment).commit()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val FRAGMENT_TAG = "Fragment"
    }
}