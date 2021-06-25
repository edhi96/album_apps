package com.sarwoedhi.albumapps.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.data.response.MovieResponse
import com.sarwoedhi.albumapps.data.source.AlbumRepository
import com.sarwoedhi.albumapps.utils.EspressoIdlingResource
import com.sarwoedhi.albumapps.utils.Resource
import okhttp3.ResponseBody
import retrofit2.*

class MainViewModel(private val repository: AlbumRepository):ViewModel() {

    val movieResult : MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val downloadResult : MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    fun getAllMovies(): LiveData<Resource<List<Movie>>> {
        EspressoIdlingResource.increment()
        movieResult.postValue(Resource.loading(null))
        repository.getAllMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
                if (!response.body()?.results.isNullOrEmpty()) {
                    movieResult.postValue(Resource.success(response.body()?.results))
                } else {
                    movieResult.postValue(Resource.error(response.message(), arrayListOf()))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movieResult.postValue(Resource.error("${t.message}", arrayListOf()))
            }

        })
        return movieResult
    }


    fun getDownloadFile(path:String?):LiveData<Resource<ResponseBody>>{
        downloadResult.postValue(Resource.loading(null))
        repository.downloadPicture(path).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    val body = response.body()
                    if(response.isSuccessful && body!=null){
                        downloadResult.postValue(Resource.success(response.body()))
                    }else {
                        downloadResult.postValue(Resource.error("Error", null))
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    downloadResult.postValue(Resource.error("${t.message}", null))
                }

            })

        return downloadResult
    }

    init {
        getAllMovies()
    }
}