package com.sarwoedhi.albumapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sarwoedhi.albumapps.BuildConfig.IMAGE_URL
import com.sarwoedhi.albumapps.R
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.databinding.ItemLayoutBinding

class AlbumAdapter(private var albumList : ArrayList<Movie>) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position],position)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie,idx:Int)
    }


    inner class ViewHolder(private var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Movie,idx:Int) {
            Glide.with(itemView.context).load(IMAGE_URL+album.backdropPath).fitCenter().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(true).into(binding.ivImgPlayer)
            binding.tvTitlePlayer.text = album.title
            binding.tvDescriptionPlayer.text = album.overview
            binding.tvRatingPlayer.max = 10
            binding.tvRatingPlayer.stepSize=0.5.toFloat()
            binding.tvRatingPlayer.rating = (album.voteAverage?:0f).toFloat()
            binding.ivDownload.setImageResource(R.drawable.ic_download)
            if(album.downloaded) binding.ivDownload.visibility = View.GONE else binding.ivDownload.visibility = View.VISIBLE
            if(album.isDownloading) binding.progressDownload.visibility = View.VISIBLE else binding.progressDownload.visibility = View.GONE
            binding.rootLayout.setOnClickListener {
                onItemClickCallback?.onItemClicked(album,idx)
                notifyDataSetChanged()
            }
        }
    }
}