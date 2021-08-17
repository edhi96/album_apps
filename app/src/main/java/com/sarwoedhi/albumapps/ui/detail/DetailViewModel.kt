package com.sarwoedhi.albumapps.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.data.source.local.favorite.FavoriteRepository
import com.sarwoedhi.albumapps.utils.Params
import com.sarwoedhi.albumapps.utils.Resource

class DetailViewModel (private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val favMovieResult : MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    private val movieCheck : MutableLiveData<Resource<Boolean>> = MutableLiveData()
    private val deletedCondition : MutableLiveData<Resource<Boolean>> = MutableLiveData()

    fun getListMovieFavorite(): MutableLiveData<Resource<List<Movie>>> {
        favMovieResult.postValue(Resource.loading(null))
        val data = favoriteRepository.getListMovieFavorite(Params.KEY_MOVIE_FAVOURITE)
        if(data.isNotEmpty()){
            favMovieResult.postValue(Resource.success(data))
        }else{
            favMovieResult.postValue(Resource.error("Fav Movie Empty", arrayListOf()))
        }
        return favMovieResult
    }

    fun checkMovie(movie:Movie): MutableLiveData<Resource<Boolean>> {
        movieCheck.postValue(Resource.loading(false))
        val data = favoriteRepository.getMovieFromFavorite(Params.KEY_MOVIE_FAVOURITE,movie)
        if(data) movieCheck.postValue(Resource.success(data))
        else movieCheck.postValue(Resource.error("",false))
        return movieCheck
    }

    fun saveMovieToFavorite(movie: Movie) {
        favoriteRepository.saveMovieToFavorite(Params.KEY_MOVIE_FAVOURITE,movie)
    }

    fun deleteFromFavourite(data: Movie):Boolean {
        deletedCondition.postValue(Resource.loading(false))
        val resultCheck = favoriteRepository.deleteFromFavorite(Params.KEY_MOVIE_FAVOURITE,data)
        if(resultCheck) movieCheck.postValue(Resource.success(resultCheck))
        else movieCheck.postValue(Resource.error("",false))
        return resultCheck
    }
}