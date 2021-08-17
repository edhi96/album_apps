package com.sarwoedhi.albumapps.ui.main.favorite.favorite_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.data.source.local.favorite.FavoriteRepository
import com.sarwoedhi.albumapps.utils.Params
import com.sarwoedhi.albumapps.utils.Resource

class FavoriteMovieViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val favMovieResult : MutableLiveData<Resource<MutableList<Movie>>> = MutableLiveData()

     fun getListMovieFavorite(): MutableLiveData<Resource<MutableList<Movie>>> {
         favMovieResult.postValue(Resource.loading(null))
         val data = favoriteRepository.getListMovieFavorite(Params.KEY_MOVIE_FAVOURITE)
         if(data.isNotEmpty()){
             favMovieResult.postValue(Resource.success(data))
         }else{
             favMovieResult.postValue(Resource.error("Fav Movie Empty", arrayListOf()))
         }
         return favMovieResult
    }

    init {
        getListMovieFavorite()
    }

}