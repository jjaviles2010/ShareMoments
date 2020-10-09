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
import com.google.android.material.snackbar.Snackbar
import com.jlapps.sharemoments.R
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.utils.toDate
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

    val photoDetailsViewModel: PhotoDetailsViewModel by viewModel()
    val photo: Photo by inject()
    var operation = "CREATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        extractPhotoInfo()
        configureObservers()
        setupActionButtons()
        setDetailsVisibility()
    }

    private fun extractPhotoInfo() {
        val photoParameter: Photo? = intent.getParcelableExtra("PHOTO")
        if(photoParameter != null) {
            photo.idPhoto = photoParameter.idPhoto
            photo.title = photoParameter.title
            photo.fileName = photoParameter.fileName
            photo.filePath = photoParameter.filePath
            photo.fileFormat = photoParameter.fileFormat
            photo.height = photoParameter.height
            photo.width = photoParameter.width
            photo.photoDate = photoParameter.photoDate
            photo.photoRating = photoParameter.photoRating
            photo.fileSize = photoParameter.fileSize
            operation = "UPDATE"
            setPhotoDetails()
        }
    }

    private fun configureObservers() {
        photoDetailsViewModel.isLoading.observe(this, Observer {
            if (it == true) {
                containerLoading.visibility = View.VISIBLE
            } else {
                containerLoading.visibility = View.GONE
            }
        })

        photoDetailsViewModel.createdSuccess.observe(this, Observer {
            if (it == true) {
                Snackbar.make(photoDetailsCoordinator, getString(R.string.msg_photo_saved), Snackbar.LENGTH_LONG).show()
                finish()
            } else {
                Snackbar.make(photoDetailsCoordinator, getString(R.string.msg_error_saving_photo), Snackbar.LENGTH_LONG).show()
                finish()
            }
        })

        photoDetailsViewModel.updatedSuccess.observe(this, Observer {
            if (it == true) {
                Snackbar.make(photoDetailsCoordinator, getString(R.string.msg_success_update), Snackbar.LENGTH_LONG).show()
                finish()
            } else {
                Snackbar.make(photoDetailsCoordinator, getString(R.string.msg_error_update), Snackbar.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun setupActionButtons() {
        fabTakePhoto.setOnClickListener { openCameraToTakePhoto() }
        fabSavePhoto.setOnClickListener { savePhotoInfo() }
        fabSharePhoto.setOnClickListener { sharePhoto() }
    }

    private fun openCameraToTakePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Snackbar.make(photoDetailsCoordinator, getString(R.string.msg_no_camera), Snackbar.LENGTH_LONG).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.jlapps.sharemoments.fileprovider",
                        it
                    )
                    photo.filePath = photoFile.absolutePath
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
        if (operation == "CREATE") {
            photoDetailsViewModel.insertPhoto(photo)
        } else {
            photoDetailsViewModel.updatePhoto(photo)
        }
    }

    private fun populateInfo() {
        if (etPhotoTitle.text.isNotEmpty()) {
            photo.title = etPhotoTitle.text.toString()
        }
        photo.photoRating = rbPhotoDetails.rating
    }

    private fun sharePhoto() {
        val sharePhotoIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, Uri.parse(photo.filePath))
            type = "image/jpeg"
        }

        startActivity(Intent.createChooser(sharePhotoIntent, getString(R.string.lb_sharePhoto)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoDetailsViewModel.isLoading.value = true
            setPhotoDetails()
            setDetailsVisibility()
            photoDetailsViewModel.isLoading.value = false
        }
    }

    private fun setPhotoDetails() {
        setPic()
        tvPhotoDate.text = photo.photoDate.toDate()
        photo.fileSize = File(photo.filePath).length()/1024/1024
        tvPhotoSize.text = "${photo.fileSize} MB"
        tvHeight.text = photo.height.toString()
        tvWidth.text = photo.width.toString()
        if (photo.title != photo.fileName) {
            etPhotoTitle.setText(photo.title)
        }
        rbPhotoDetails.rating = photo.photoRating
    }

    private fun setDetailsVisibility() {
        if (photo.filePath.isNotEmpty()) {
            grp_details_fields.visibility = View.VISIBLE
            fabSavePhoto.visibility = View.VISIBLE
            fabSharePhoto.visibility = View.VISIBLE
        } else {
            grp_details_fields.visibility = View.GONE
            fabSavePhoto.visibility = View.GONE
            fabSharePhoto.visibility = View.GONE
        }

        if (operation == "UPDATE") {
            fabTakePhoto.visibility = View.GONE
            rbPhotoDetails.setIsIndicator(true)
        }
    }


    private fun setPic() {
        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(photo.filePath, this)

            photo.width = outWidth
            photo.height = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = Math.max(1, Math.min(photo.width / 1080, photo.height / 900))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        BitmapFactory.decodeFile(photo.filePath, bmOptions)?.also { bitmap ->
            ivPhotoDetails.setImageBitmap(bitmap)
        }
    }
}