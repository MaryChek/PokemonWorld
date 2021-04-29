package com.example.work_with_service.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.work_with_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}