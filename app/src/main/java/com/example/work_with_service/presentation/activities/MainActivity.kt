package com.example.work_with_service.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}