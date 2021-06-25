package com.sarwoedhi.albumapps.ui.main

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarwoedhi.albumapps.BuildConfig.IMAGE_URL
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.ActivityMainBinding
import com.sarwoedhi.albumapps.ui.adapter.AlbumAdapter
import com.sarwoedhi.albumapps.utils.Status
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.*


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private var movieList = arrayListOf<Movie>()
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var binding: ActivityMainBinding
    private var urlFile = ""
    private var idxPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMovies()
        loadAllMovies()
    }


    private fun loadAllMovies() {
        val albumDataView = mainViewModel.getAllMovies()
        albumDataView.observe(this@MainActivity, {
            movieList.clear()
            binding.progressProduct.visibility = View.GONE
            when (it.status) {
                Status.SUCCESS -> {
                    binding.tvEmptyData.visibility = View.GONE
                    movieList.addAll(it.data!!)
                }
                Status.LOADING -> {
                    binding.tvEmptyData.visibility = View.GONE
                    binding.progressProduct.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.tvEmptyData.text = it.message
                    binding.tvEmptyData.visibility = View.VISIBLE
                }
            }
            albumAdapter.notifyDataSetChanged()
        })
    }

    private fun initMovies() {
        albumAdapter = AlbumAdapter(movieList)
        albumAdapter.setOnItemClickCallback(object : AlbumAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie,idx:Int) {
                    if(data.posterPath!=null){
                        urlFile = data.posterPath!!
                        idxPosition = idx
                        movieList[idx].isDownloading = true
                        downloadAlbum()
                    }
            }
        }
        )
        binding.rvMovies.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            isNestedScrollingEnabled = true
        }
    }

    private fun downloadAlbum(){
        val downloadData = mainViewModel.getDownloadFile("$IMAGE_URL${urlFile}")
        downloadData.observe(this@MainActivity, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.tvEmptyData.visibility = View.GONE
                    lifecycleScope.launch { saveFile(it.data, urlFile) }
                }
                Status.LOADING -> { }
                Status.ERROR -> {
                    binding.progressProduct.visibility = View.GONE
                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun saveFile(data:ResponseBody?,url:String){
        val file = File("${Environment.getExternalStorageDirectory().path}/download/${url}")
        file.createNewFile()
        if(data!=null){
            val  inStream = data.source().inputStream()
            val outStream = file.outputStream()
            outStream.flush()
            val contentLength = data.contentLength()
            var currentLength = 0L
            val buff = ByteArray(1024 * 4)
            var len = inStream.read(buff)
            var percent = 0
            while (len != -1) {
                outStream.write(buff, 0, len)
                currentLength += len
                if(((currentLength * 100).div(contentLength) ).toInt()>percent) {
                    percent = (currentLength / contentLength * 100).toInt()
                }
                len = inStream.read(buff)
            }
            lifecycleScope.launch {
                delay(1000)
                movieList[idxPosition].downloaded = true
                movieList[idxPosition].isDownloading = false
                albumAdapter.notifyItemChanged(idxPosition)
                Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
            }
            outStream.close()
        }

    }

}

