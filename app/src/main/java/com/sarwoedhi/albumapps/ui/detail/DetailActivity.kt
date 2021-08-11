package com.sarwoedhi.albumapps.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.models.AnimationSet
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.ActivityDetailBinding
import com.sarwoedhi.albumapps.utils.Params

class DetailActivity : AppCompatActivity() {

    companion object{
        fun newInstance(ctx:Context,dataMovie:Movie){
            val dataIntent = Intent(ctx,DetailActivity::class.java)
            dataIntent.putExtra(Params.EXTRA_KEY_MOVIE,dataMovie)
            ctx.startActivity(dataIntent)
        }
    }

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

    private lateinit var binding: ActivityDetailBinding

    private var dataMovie: Movie?=null

    private fun initBundle(){
        dataMovie = intent.getParcelableExtra(Params.EXTRA_KEY_MOVIE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAnimation()
        initBundle()
        initView(dataMovie)
        initAction()
    }

    private fun initAction(){

        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initView(dataIntent:Movie?){
        val imgBASEURL = "https://image.tmdb.org/t/p/original/"
        binding.toolbar.tvTitleBar.text = dataIntent?.title
        binding.tvDetailNameMovie.text = dataIntent?.title
        binding.tvDetailDescMovie.text = dataIntent?.overview
        binding.tvPopularity.text = dataIntent?.popularity?.toString()
        binding.tvReleaseDate.text = dataIntent?.releaseDate
        Glide.with(this).load(imgBASEURL +dataIntent?.backdropPath).centerCrop().into(binding.imgDetailMovie)
    }

    override fun finish() {
        super.finish()
        if (null != overrideAnimation) {
            overridePendingTransition(
                overrideAnimation!!.animExitIn,
                overrideAnimation!!.animExitOut
            )
        }
    }
}