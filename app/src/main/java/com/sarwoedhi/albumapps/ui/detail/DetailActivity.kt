package com.sarwoedhi.albumapps.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.event.FavEvent
import com.sarwoedhi.albumapps.data.models.AnimationSet
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.ActivityDetailBinding
import com.sarwoedhi.albumapps.utils.Params
import com.sarwoedhi.albumapps.utils.RxBus
import com.sarwoedhi.albumapps.utils.Status
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        fun newInstance(ctx: Context, dataMovie: Movie) {
            val dataIntent = Intent(ctx, DetailActivity::class.java)
            dataIntent.putExtra(Params.EXTRA_KEY_MOVIE, dataMovie)
            ctx.startActivity(dataIntent)
        }
    }

    private var overrideAnimation: AnimationSet? = null
        set(value) {
            if (null != value) {
                overridePendingTransition(value.animEnterIn, value.animEnterOut)
                field = value
            }
        }

    private val viewModel: DetailViewModel by viewModel()
    private fun initAnimation() {
        overrideAnimation =
            AnimationSet(R.anim.enter, R.anim.stay_in_place, R.anim.stay_in_place, R.anim.exit)
    }

    private lateinit var binding: ActivityDetailBinding

    private var dataMovie: Movie? = null
    private var isFavorite = false

    private fun initBundle() {
        dataMovie = intent.getParcelableExtra(Params.EXTRA_KEY_MOVIE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAnimation()
        initBundle()
        initView(dataMovie)
        isMovieFavorite()
        initAction()
    }

    private fun initAction() {

        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.toolbar.ivFavorite.setOnClickListener {
            if (dataMovie != null) {
                if(isFavorite){
                    viewModel.deleteFromFavourite(dataMovie!!)
                    Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        binding.toolbar.ivFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                    }
                }else{
                    viewModel.saveMovieToFavorite(dataMovie!!)
                    Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        binding.toolbar.ivFavorite.setImageResource(R.drawable.ic_favorite_24)
                    }

                }
                isMovieFavorite()
                RxBus.publish(FavEvent())
                checkMovie()
            }
        }
    }

    private fun isMovieFavorite() {
        if (dataMovie != null) {
            viewModel.checkMovie(dataMovie!!).observe(this, {
                isFavorite = it.status == Status.SUCCESS
                binding.toolbar.ivFavorite.setImageResource(if (it.status == Status.SUCCESS) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24)
            })
        }
    }

    private fun checkMovie() {
        viewModel.getListMovieFavorite().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("hasil", "success ${it.data?.get(0)?.title} ---- ${it.data?.size}")
                }
                Status.LOADING -> {
                    Log.d("hasil", "LOADING ${it.data?.size}")
                }
                Status.ERROR -> {
                    Log.d("hasil", "ERROR ${it.data?.size}")
                }
            }
        })
    }

    private fun initView(dataIntent: Movie?) {
        val imgBASEURL = "https://image.tmdb.org/t/p/original/"
        binding.toolbar.tvTitleBar.text = dataIntent?.title
        binding.tvDetailNameMovie.text = dataIntent?.title
        binding.tvDetailDescMovie.text = dataIntent?.overview
        binding.tvPopularity.text = dataIntent?.popularity?.toString()
        binding.tvReleaseDate.text = dataIntent?.releaseDate
        Glide.with(this).load(imgBASEURL + dataIntent?.backdropPath).centerCrop().into(binding.imgDetailMovie)
        binding.toolbar.ivFavorite.setImageResource(R.drawable.ic_favorite_border_24)
    }

    override fun finish() {
        super.finish()
        if (null != overrideAnimation) {
            overridePendingTransition(overrideAnimation!!.animExitIn, overrideAnimation!!.animExitOut)
        }
    }
}