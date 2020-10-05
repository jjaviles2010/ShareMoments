package com.jlapps.sharemoments.view.photos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
            ivPhoto.setImageBitmap(setPic(photoInfo.filePath))
            setOnClickListener{
                clickListener(photoInfo)
            }
        }

        private fun setPic(photoPath: String) : Bitmap {

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true

                BitmapFactory.decodeFile(photoPath, this)

                val photoW: Int = outWidth
                val photoH: Int = outHeight

                // Determine how much to scale down the image
                val scaleFactor: Int = Math.max(1, Math.min(photoW / 200, photoH / 200))

                // Decode the image file into a Bitmap sized to fill the View
                inJustDecodeBounds = false
                inSampleSize = scaleFactor
            }
            return BitmapFactory.decodeFile(photoPath, bmOptions)
        }
    }
}