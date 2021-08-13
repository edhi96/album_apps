package com.sarwoedhi.albumapps.ui.dialog.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.FragmentDetailItemDialogBinding
import com.sarwoedhi.albumapps.utils.Params

class DetailItemDialogFragment : BottomSheetDialogFragment() {

    companion object{
        fun newInstance(movie: Movie): DetailItemDialogFragment {
            val fragment = DetailItemDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(Params.BUNDLE_DETAIL_DATA_DIALOG,movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding : FragmentDetailItemDialogBinding
    private var detailMovie:Movie? = null
    private var onButtonClickListener: OnButtonClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DefaultDialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailItemDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadArguments()
        initView()
        initAction()
    }

    private fun loadArguments() {
        arguments?.let {
            detailMovie = it.getParcelable(Params.BUNDLE_DETAIL_DATA_DIALOG)
        }
    }

    private fun initView(){
        binding.tvTitleDetail.text = detailMovie?.title
        binding.tvDescriptionDetail.text = detailMovie?.overview
        Glide.with(this).load("https://image.tmdb.org/t/p/original/" +detailMovie?.backdropPath).centerCrop().into(binding.imgDetailMovie)
    }

    private fun initAction(){
        binding.btnOk.setOnClickListener {
            onButtonClickListener?.onButtonClick(movie = detailMovie)
            dismissAllowingStateLoss()
        }
    }

    fun setOnButtonClickCallback(onItemClickCallback: OnButtonClickListener) {
        this.onButtonClickListener = onItemClickCallback
    }

    interface OnButtonClickListener {
        fun onButtonClick(movie: Movie?)
    }

}