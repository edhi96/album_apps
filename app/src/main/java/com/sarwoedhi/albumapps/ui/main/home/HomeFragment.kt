package com.sarwoedhi.albumapps.ui.main.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.FragmentHomeBinding
import com.sarwoedhi.albumapps.ui.adapter.AlbumAdapter
import com.sarwoedhi.albumapps.ui.detail.DetailActivity
import com.sarwoedhi.albumapps.ui.dialog.info_confirmation.InfoConfirmationDialog
import com.sarwoedhi.albumapps.ui.dialog.bottom_sheet.DetailItemDialogFragment
import com.sarwoedhi.albumapps.utils.Status
import okhttp3.MultipartBody
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var movieList = arrayListOf<Movie>()
    private var searchMovieList = arrayListOf<Movie>()
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initMovies()
        loadAllMovies()
        searchFunction()
        return binding.root
    }

    private fun searchFunction() {
        binding.svMovie.queryHint = resources.getString(R.string.title_movie)
        binding.svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 3) {
                    searchMovieList.clear()
                    homeViewModel.getSearchMovies(newText).observe(requireActivity(), {
                        searchMovieList.clear()
                        binding.progressHome.visibility = View.GONE
                        when (it.status) {
                            Status.SUCCESS -> {
                                binding.tvEmptyMovieHome.visibility = View.GONE
                                searchMovieList.addAll(it.data!!)
                            }
                            Status.LOADING -> {
                                binding.tvEmptyMovieHome.visibility = View.GONE
                                binding.progressHome.visibility = View.VISIBLE
                            }
                            Status.ERROR -> {
                                binding.tvEmptyMovieHome.text = it.message
                                binding.tvEmptyMovieHome.visibility = View.VISIBLE
                            }
                        }
                        albumAdapter = AlbumAdapter(searchMovieList)
                        binding.rvHome.adapter = albumAdapter
                    })
                } else {
                    albumAdapter = AlbumAdapter(movieList)
                    binding.rvHome.adapter = albumAdapter
                }
                return true
            }
        })
    }

    private fun loadAllMovies() {
        val albumDataView = homeViewModel.getAllHomeMovies()
        albumDataView.observe(requireActivity(), {
            movieList.clear()
            binding.progressHome.visibility = View.GONE
            when (it.status) {
                Status.SUCCESS -> {
                    binding.tvEmptyMovieHome.visibility = View.GONE
                    movieList.addAll(it.data!!)
                }
                Status.LOADING -> {
                    binding.tvEmptyMovieHome.visibility = View.GONE
                    binding.progressHome.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.tvEmptyMovieHome.text = it.message
                    binding.tvEmptyMovieHome.visibility = View.VISIBLE
                }
            }
            albumAdapter.notifyDataSetChanged()
        })
    }

    private fun initMovies() {
        albumAdapter = AlbumAdapter(movieList)
        albumAdapter.setOnItemClickCallback(object : AlbumAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie, idx: Int) {
                when (idx) {
                    0 -> {
                        val dialogInfo = InfoConfirmationDialog.newInstance("Succes", data, "OK", R.drawable.ic_action_success)
                        dialogInfo.show(childFragmentManager, HomeFragment::class.java.canonicalName)
                        dialogInfo.isCancelable = false
                        dialogInfo.setOnYesListener {
                            dialogInfo.dismiss()
                        }
                    }
                    1 -> {
                        val dialogInfo = DetailItemDialogFragment.newInstance(data)
                        dialogInfo.show(childFragmentManager, HomeFragment::class.java.canonicalName)
                        dialogInfo.setOnButtonClickCallback(object :
                            DetailItemDialogFragment.OnButtonClickListener {
                            override fun onButtonClick(movie: Movie?) {
                                Toast.makeText(requireContext(), "movie judul ${movie?.title}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    else -> {
                        DetailActivity.newInstance(requireActivity(), data)
                    }
                }

            }
        }
        )
        binding.rvHome.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = true
        }
    }

    private var filePath: Uri? = null
    private var bodyKtp: MultipartBody.Part? = null //todo: ini yang akan dimasukkan

//     fun examplePostUpdate(){
//
//       val bitmap = getImageResized(this, filePath!!) // file BITMAP
//       val files = File(filePath.toString()) // tipe data URI
//       val newFile =  persistImage(bitmap, files.name.getUrlFileName()) // menyimpan file
//       val mFile: RequestBody = newFile.toRequestBody("image/*".toMediaTypeOrNull(), newFile) //membungkus file ke dalam request body
//       bodyKtp = MultipartBody.Part.createFormData("avatar_user", newFile.name, mFile) // membuat formdata multipart berisi request body
//    }
//


}