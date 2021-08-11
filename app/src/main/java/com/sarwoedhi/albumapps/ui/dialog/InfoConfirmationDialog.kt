package com.sarwoedhi.albumapps.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.FragmentInfoDialogBinding
import com.sarwoedhi.albumapps.utils.Params

class InfoConfirmationDialog : DialogFragment() {

    companion object{
        fun newInstance(title:String,movie: Movie,btnText:String,ivIcon:Int): InfoConfirmationDialog {
            val fragment = InfoConfirmationDialog()
            val bundle = Bundle()
            bundle.putString(Params.BUNDLE_TITLE_DIALOG,title)
            bundle.putParcelable(Params.BUNDLE_DESC_DIALOG,movie)
            bundle.putString(Params.BUNDLE_BUTTON_DIALOG,btnText)
            bundle.putInt(Params.BUNDLE_ICON_DIALOG,ivIcon)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var title: String = ""
    private var detailMovie: Movie? = null
    private var btnText: String = ""
    private var iconPicture: Int = 0
    private var movie:Movie? = null

    private lateinit var binding: FragmentInfoDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInfoDialogBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
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
            title = it.getString("BUNDLE_TITLE_DIALOG")?:""
        }
        arguments?.let {
            detailMovie = it.getParcelable("BUNDLE_DESC_DIALOG")
        }
        arguments?.let {
            btnText = it.getString("BUNDLE_BUTTON_DIALOG")?:""
        }
        arguments?.let {
            iconPicture = it.getInt("BUNDLE_ICON_DIALOG")
        }
    }


    private fun initView() {
        binding.tvTitle.text = title
        binding.tvDesc.text = detailMovie?.title
        binding.ivIcon.setImageResource(iconPicture)
        binding.btYes.text = btnText
    }

    private fun initAction() {
        binding.btYes.setOnClickListener {
            onYesListener()
            dismiss()
        }
    }

    private var onYesListener: () -> Unit = { }

    fun setOnYesListener(listener: () -> Unit) {
        this.onYesListener = listener
    }

}