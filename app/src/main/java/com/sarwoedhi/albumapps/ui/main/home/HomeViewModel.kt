package com.sarwoedhi.albumapps.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.data.response.MovieResponse
import com.sarwoedhi.albumapps.data.source.MovieRepository
import com.sarwoedhi.albumapps.utils.EspressoIdlingResource
import com.sarwoedhi.albumapps.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: MovieRepository):ViewModel() {

    val movieHomeResult : MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    fun getAllHomeMovies(): LiveData<Resource<List<Movie>>> {
        EspressoIdlingResource.increment()
        movieHomeResult.postValue(Resource.loading(null))
        repository.getAllMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
                if (!response.body()?.results.isNullOrEmpty()) {
                    movieHomeResult.postValue(Resource.success(response.body()?.results))
                } else {
                    movieHomeResult.postValue(Resource.error(response.message(), arrayListOf()))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movieHomeResult.postValue(Resource.error("${t.message}", arrayListOf()))
            }

        })
        return movieHomeResult
    }

    init {
        getAllHomeMovies()
    }
}