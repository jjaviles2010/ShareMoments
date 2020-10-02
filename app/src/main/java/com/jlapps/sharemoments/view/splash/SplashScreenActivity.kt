package com.jlapps.sharemoments.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jlapps.sharemoments.view.photos.PhotosListActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, PhotosListActivity::class.java)
        startActivity(intent)
        finish()
    }
}