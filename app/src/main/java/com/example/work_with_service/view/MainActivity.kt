package com.example.work_with_service.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.work_with_service.DetailPage
import com.example.work_with_service.R
import com.example.work_with_service.adapter.PagerAdapter
import com.example.work_with_service.databinding.MainActivittyBinding

class MainActivity : AppCompatActivity() {
    private var binding: MainActivittyBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivittyBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initPages(savedInstanceState)
    }

    private fun initPages(savedInstanceState: Bundle?) {
            if (savedInstanceState == null) {
                val pagerPokemon = PagerPokemon()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainActivity, pagerPokemon, FRAGMENT_TAG)
                    .commit()
            } else {
                val pagerPokemon = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)!!
                supportFragmentManager.beginTransaction()
                    .attach(pagerPokemon)
                    .commit()
                println(supportFragmentManager.backStackEntryCount)
            }

    }

    override fun onPause() {
        super.onPause()
        binding = null
    }

    companion object {
        private const val FRAGMENT_TAG = "Fragment"
    }
}