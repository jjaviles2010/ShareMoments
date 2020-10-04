package com.jlapps.sharemoments.view.photos

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlapps.sharemoments.R
import com.jlapps.sharemoments.model.Photo
import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.photo_list_item.view.*

class PhotosListAdapter(
    val photos: List<Photo>,
    val clickListener: (Photo) -> Unit
): RecyclerView.Adapter<PhotosListAdapter.PhotoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.photo_list_item, parent, false)

        return PhotoViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoInfo = photos.get(position)
        holder.bindView(photoInfo, clickListener)
    }

    class PhotoViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bindView(photoInfo: Photo,
                     clickListener: (Photo) -> Unit) = with(itemView) {
            tvPhotoTitle.text = photoInfo.title
            rbPhotoRate.rating = photoInfo.photoRating
            ivPhoto.setImageURI(Uri.parse(photoInfo.filePath))
            setOnClickListener{
                clickListener(photoInfo)
            }
        }
    }
}