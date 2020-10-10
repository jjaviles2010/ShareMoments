package com.jlapps.sharemoments.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.jlapps.sharemoments.R

object PermissionUtils {
    fun checkPermissions(activity: Activity, requestCode: Int) : Boolean {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true

        if (PermissionChecker.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            return true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(activity,activity.getString(R.string.msg_request_perm_rationale), Toast.LENGTH_LONG).show()
            }

            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
            return false
        }
    }
}