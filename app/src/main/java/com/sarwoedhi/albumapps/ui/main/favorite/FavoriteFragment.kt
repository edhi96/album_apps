package com.sarwoedhi.albumapps.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.databinding.FragmentFavoriteBinding
import com.sarwoedhi.albumapps.ui.main.favorite.adapter.TabsPagerAdapter
import com.sarwoedhi.albumapps.ui.main.favorite.favorite_movie.FavoriteMovieFragment
import com.sarwoedhi.albumapps.ui.main.favorite.favorite_tv.FavoriteTvFragment
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private val listFragment = mutableListOf<Fragment>()
    private val listTitle = mutableListOf<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listFragment.add(FavoriteMovieFragment())
        listFragment.add(FavoriteTvFragment())

        listTitle.add(getString(R.string.title_movie))
        listTitle.add(getString(R.string.title_tv_show))

        val sectionsPagerAdapter = TabsPagerAdapter(requireActivity(),listFragment)
        binding.vpTabs.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.vpTabs) { tab, position ->
            tab.text = listTitle[position]
        }.attach()


    }

}