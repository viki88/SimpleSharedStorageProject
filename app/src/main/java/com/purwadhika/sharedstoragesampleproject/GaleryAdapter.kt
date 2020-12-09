package com.purwadhika.sharedstoragesampleproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.purwadhika.sharedstoragesampleproject.databinding.GaleryLayoutBinding
import com.squareup.picasso.Picasso

class GaleryAdapter : ListAdapter<MediaStoreImage, GaleryAdapter.GaleryViewHolder>(DIFF_UTILS){

    inner class GaleryViewHolder(private val binding :GaleryLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(mediaStoreImage: MediaStoreImage){
            Picasso.get().load(mediaStoreImage.contentUri).into(binding.imageView)
        }
    }

    companion object {
        var DIFF_UTILS = object : DiffUtil.ItemCallback<MediaStoreImage>(){
            override fun areItemsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleryViewHolder {
        val view = GaleryLayoutBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return GaleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GaleryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}