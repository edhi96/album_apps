package com.sarwoedhi.albumapps.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.models.AnimationSet
import com.sarwoedhi.albumapps.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var overrideAnimation: AnimationSet? = null
        set(value) {
            if (null != value) {
                overridePendingTransition(value.animEnterIn, value.animEnterOut)
                field = value
            }
        }

    private fun initAnimation() {
        overrideAnimation = AnimationSet(R.anim.enter, R.anim.stay_in_place, R.anim.stay_in_place, R.anim.exit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAnimation()
        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }

}

