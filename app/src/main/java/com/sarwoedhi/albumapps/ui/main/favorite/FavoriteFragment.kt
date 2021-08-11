package com.sarwoedhi.albumapps.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sarwoedhi.albumapps.databinding.FragmentFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private val favoriteViewModel : FavoriteViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.text.observe(viewLifecycleOwner, {
            binding.tvFavorite.text = it
        })
    }

}