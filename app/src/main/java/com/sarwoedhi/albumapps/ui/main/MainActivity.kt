package com.sarwoedhi.albumapps.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.event.FavEvent
import com.sarwoedhi.albumapps.data.models.AnimationSet
import com.sarwoedhi.albumapps.databinding.ActivityMainBinding
import com.sarwoedhi.albumapps.utils.RxBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val uiCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private var deeplink : String? = ""

    private var deviceToken : String? = ""
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
        initBundle()
        initFirebase()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAnimation()
        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        RxBus.publish(FavEvent())
    }

    fun addUiSubscription(disposable: Disposable) {
        uiCompositeDisposable.add(disposable)
    }

    fun clearAllSubscription() {
        uiCompositeDisposable.clear()
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            deviceToken = it.result
            Log.d("hasil","dilakukan ${it.result}")
        }
    }

    private fun initBundle(){
        deeplink = intent.getStringExtra("deeplink")
    }

}

