package com.sarwoedhi.albumapps.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.FragmentHomeBinding
import com.sarwoedhi.albumapps.ui.adapter.AlbumAdapter
import com.sarwoedhi.albumapps.ui.detail.DetailActivity
import com.sarwoedhi.albumapps.ui.dialog.InfoConfirmationDialog
import com.sarwoedhi.albumapps.utils.Status
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var movieList = arrayListOf<Movie>()
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initMovies()
        loadAllMovies()
        return binding.root
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
            override fun onItemClicked(data: Movie, idx:Int) {
                if(idx==0){
                    val dialogInfo = InfoConfirmationDialog.newInstance("Succes", data, "OK", R.drawable.ic_action_success)
                    dialogInfo.show(childFragmentManager, HomeFragment::class.java.canonicalName)
                    dialogInfo.isCancelable = false
                    dialogInfo.setOnYesListener {
                        dialogInfo.dismiss()
                    }
                }else{
                    DetailActivity.newInstance(requireActivity(),data)
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


}