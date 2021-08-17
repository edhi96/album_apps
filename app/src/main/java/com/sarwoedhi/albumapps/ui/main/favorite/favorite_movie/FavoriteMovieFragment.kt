package com.sarwoedhi.albumapps.ui.main.favorite.favorite_movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarwoedhi.albumapps.data.event.FavEvent
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.FragmentFavoriteMovieBinding
import com.sarwoedhi.albumapps.ui.adapter.AlbumAdapter
import com.sarwoedhi.albumapps.ui.detail.DetailActivity
import com.sarwoedhi.albumapps.utils.RxBus
import com.sarwoedhi.albumapps.utils.Status
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    lateinit var binding : FragmentFavoriteMovieBinding
    private val viewModel:FavoriteMovieViewModel by viewModel()
    private var movieList = arrayListOf<Movie>()
    private lateinit var albumAdapter: AlbumAdapter
    private val uiCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addUiSubscription(disposable: Disposable) {
        uiCompositeDisposable.add(disposable)
    }

    fun clearAllSubscription() {
        uiCompositeDisposable.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteMovieBinding.inflate(inflater,container,false)
        initMovies()
        loadAllMovies()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addUiSubscription(RxBus.listen(FavEvent::class.java).subscribe {
            loadAllMovies()
        })
    }

    private fun loadAllMovies() {
        val albumDataView = viewModel.getListMovieFavorite()
        albumDataView.observe(viewLifecycleOwner, {
            movieList.clear()
            binding.progressFav.visibility = View.GONE
            when (it.status) {
                Status.SUCCESS -> {
                    binding.tvEmptyMovieFav.visibility = View.GONE
                    binding.rvFav.visibility = View.VISIBLE
                    movieList.addAll(it.data!!)
                    initMovies()
                }
                Status.LOADING -> {
                    binding.tvEmptyMovieFav.visibility = View.GONE
                    binding.progressFav.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.tvEmptyMovieFav.text = it.message
                    binding.rvFav.visibility = View.GONE
                    binding.tvEmptyMovieFav.visibility = View.VISIBLE
                }
            }
            albumAdapter.notifyDataSetChanged()
        })
    }


    private fun initMovies() {
        albumAdapter = AlbumAdapter(movieList)

        albumAdapter.setOnItemClickCallback(object : AlbumAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie, idx: Int) {
                DetailActivity.newInstance(requireActivity(), data)
            }
        }
        )
        binding.rvFav.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = true
        }

    }

}