package com.architecture.ui.activity.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PowerPermission
import com.qifan.powerpermission.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        const val RESULT_CODE: Int = 999

        val REQUESTED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    abstract fun initView()
    abstract fun initClick()

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    private fun checkPermissions() {

        PowerPermission.init()
            .requestPermissions(
                this,
                permissions = REQUESTED_PERMISSIONS,
                RESULT_CODE
            ) { permissionResult ->

                when {
                    permissionResult.hasAllGranted() -> {
                        doPermissionAllGrantedWork(permissionResult.granted())
                    }
                    permissionResult.hasRational() -> {
                        doPermissionReasonWork(permissionResult.rational())
                    }
                    permissionResult.hasPermanentDenied() -> {
                        doPermissionPermanentWork(permissionResult.permanentDenied())
                    }
                }
            }
    }

    private fun doPermissionAllGrantedWork(granted: List<Permission>) {
        Timber.d("doPermissionAllGrantedWork: $granted")
    }

    private fun doPermissionReasonWork(rational: List<Permission>) {
        Timber.d("doPermissionReasonWork: $rational")
        checkPermissions()
    }

    private fun doPermissionPermanentWork(permanentDenied: List<Permission>) {
        Timber.d("doPermissionPermanentWork: $permanentDenied")

        // now, user has denied permission permanently!
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Grant Permission")
        builder.setMessage(
            "You have previously declined this permission. The app needs this permission to properly run it. \n\n" +
                    "The app will open settings to change the permission from there."
        )

        builder.setPositiveButton("OK") { _, _ ->

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            resultLauncher.launch(intent)
        }

        builder.show()
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            checkPermissions()
        }
    }

//    fun <T : ViewDataBinding> setLayout(layout : Int) : T {
//        return DataBindingUtil.setContentView(this, layout)
//    }

}