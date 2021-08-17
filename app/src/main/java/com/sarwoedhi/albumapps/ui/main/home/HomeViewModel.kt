package com.sarwoedhi.albumapps.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import com.sarwoedhi.albumapps.data.source.remote.movie.MovieRepository
import com.sarwoedhi.albumapps.utils.Resource
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: MovieRepository):ViewModel() {

    val movieHomeResult : MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val movieSearchResult : MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    var profileUpdateEntity:MutableLiveData<Any> =  MutableLiveData()
    var detailEntity:MutableLiveData<Any> =  MutableLiveData()

    fun getAllHomeMovies(): LiveData<Resource<List<Movie>>> {
        movieHomeResult.postValue(Resource.loading(null))
        repository.getAllMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
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

    fun getSearchMovies(query:String): LiveData<Resource<List<Movie>>> {
        movieSearchResult.postValue(Resource.loading(null))
        repository.getSearchMovies(query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (!response.body()?.results.isNullOrEmpty()) {
                    movieSearchResult.postValue(Resource.success(response.body()?.results))
                } else {
                    movieSearchResult.postValue(Resource.error(response.message(), arrayListOf()))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movieSearchResult.postValue(Resource.error("${t.message}", arrayListOf()))
            }

        })
        return movieSearchResult
    }

    /* todo : only example*/
    fun postLogin(email:String,password:String,deviceFirebaseToken:String):LiveData<Any>{
        detailEntity =  MutableLiveData()
        val call = repository.postLogin(email,password,deviceToken = deviceFirebaseToken)
        call.enqueue(object : Callback<DetailMovieResponse> {
            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>
            ) {
                val body = response.body()
                if (response.code() == 200 && body != null)
                {
                    detailEntity.postValue(body)
                } else{
                    val json = JSONObject(response.errorBody()?.string())
                    val errorMessageEmail = json.getJSONObject("errors")
                    if(errorMessageEmail!=null){
                        detailEntity.postValue("$errorMessageEmail")
                    }
                }
            }
        })
        return detailEntity
    }

    /* todo : only example*/
    fun putEditProfileWithPhoto(token:String,name:String,email:String,phone:String,photo: MultipartBody.Part): LiveData<Any> {
        profileUpdateEntity.postValue(Resource.loading(null))
        repository.updateProfileWithPhoto(token = token,nameProfile = name,emailProfile = email,phoneProfile = phone,profilePhoto = photo).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                if (response.code()==200) {
                    profileUpdateEntity.postValue(Resource.success("Success"))
                } else {
                    profileUpdateEntity.postValue(Resource.error("Error Update", null))
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                profileUpdateEntity.postValue(Resource.error("Error Update", null))
            }

        })
        return profileUpdateEntity
    }

    init {
        getAllHomeMovies()
    }
}