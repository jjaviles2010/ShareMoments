package com.jlapps.sharemoments.view.photoDetails

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.jlapps.sharemoments.R
import com.jlapps.sharemoments.model.Photo
import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.include_loading.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class PhotoDetailsActivity : AppCompatActivity() {

    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String

    val photoDetailsViewModel: PhotoDetailsViewModel by viewModel()
    val photo: Photo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)

        configureObservers()
        setupActionButtons()
        setDetailsVisibility()
    }

    private fun configureObservers() {
        photoDetailsViewModel.isLoading.observe(this, Observer {
            if (it == true) {
                containerLoading.visibility = View.VISIBLE
            } else {
                containerLoading.visibility = View.GONE
            }
        })

        photoDetailsViewModel.message.observe(this, Observer {
            if (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_SHORT)
                finish()
            }
        })
    }

    private fun setupActionButtons() {
        fabTakePhoto.setOnClickListener { openCameraToTakePhoto() }
        fabSavePhoto.setOnClickListener { savePhotoInfo() }
    }

    private fun openCameraToTakePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    photoDetailsViewModel.message.value = "Camera is not available"
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.jlapps.sharemoments.fileprovider",
                        it
                    )
                    photo.filePath = photoURI.toString()
                    photo.fileName = photoFile.name
                    photo.title = photoFile.nameWithoutExtension
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val date = Date()
        photo.photoDate = date.time
        val timeStamp: String = SimpleDateFormat("yyyyMMdd").format(date)
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun savePhotoInfo() {
        populateInfo()
        photoDetailsViewModel.insertPhoto(photo)
    }

    private fun populateInfo() {
        if (etPhotoTitle.text.isNotEmpty()) {
            photo.title = etPhotoTitle.text.toString()
        }
        photo.photoRating = rbPhotoDetails.rating
    }

    private fun setDetailsVisibility() {
        if (photo.filePath.isNotEmpty()) {
            grp_details_fields.visibility = View.VISIBLE
            fabSavePhoto.visibility = View.VISIBLE
        } else {
            grp_details_fields.visibility = View.GONE
            fabSavePhoto.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoDetailsViewModel.isLoading.value = true
            ivPhotoDetails.setImageURI(Uri.parse(photo.filePath))
            setDetailsVisibility()
            photoDetailsViewModel.isLoading.value = false
            //setPic()
        }
    }


    private fun setPic() {
        // Get the dimensions of the View
        val targetW: Int = ivPhotoDetails.width
        val targetH: Int = ivPhotoDetails.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight


            // Determine how much to scale down the image
            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            ivPhotoDetails.setImageBitmap(bitmap)
        }
    }
}