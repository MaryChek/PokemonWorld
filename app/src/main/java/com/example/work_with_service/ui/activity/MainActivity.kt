package com.example.work_with_service.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ActivityMainBinding
import com.example.work_with_service.ui.fragment.pager.PagerPokemonFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        attachRootFragment(savedInstanceState)
    }

    private fun attachRootFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val pagerPokemon = PagerPokemonFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainActivity, pagerPokemon, null).commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}