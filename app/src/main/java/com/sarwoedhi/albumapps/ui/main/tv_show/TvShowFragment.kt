package com.sarwoedhi.albumapps.ui.main.tv_show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sarwoedhi.albumapps.databinding.FragmentTvShowBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val tvShowViewModel: TvShowViewModel by viewModel()
    private lateinit var binding : FragmentTvShowBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowViewModel.text.observe(viewLifecycleOwner, {
            binding.tvTvShow.text = it
        })
    }
}