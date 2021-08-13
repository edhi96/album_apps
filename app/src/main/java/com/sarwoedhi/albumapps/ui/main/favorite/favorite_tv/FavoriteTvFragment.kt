package com.sarwoedhi.albumapps.ui.main.favorite.favorite_tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.databinding.FragmentFavoriteTvBinding

class FavoriteTvFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteTvBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFavoriteTvBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}