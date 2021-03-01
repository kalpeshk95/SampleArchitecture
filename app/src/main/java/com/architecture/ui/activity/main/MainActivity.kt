package com.architecture.ui.activity.main

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.architecture.R
import com.architecture.databinding.ActivityMainBinding
import com.architecture.ex.gone
import com.architecture.ex.visible
import com.architecture.ui.activity.base.BaseActivity
import com.architecture.ui.activity.login.LoginActivity
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.utils.Constant
import com.architecture.utils.Log
import java.io.File
import java.util.*

class MainActivity : BaseActivity(), BaseFragment.ShowProgressBar {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val cameraPerm = arrayOf(Manifest.permission.CAMERA)
    private val storagePerm = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val camStoragePerm = cameraPerm + storagePerm

    private lateinit var sdCardPath: String

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sdCardPath = getExternalFilesDir(null)?.absolutePath + "/SampleArch"

        initView()
        initClick()
    }

    override fun initView() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

//        navController = Navigation.findNavController(this, R.id.fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            showHeader(destination.label?.toString())
        }

        val root = createImageFile()
        if (root.exists()) {
            val selectedImage = BitmapFactory.decodeFile(root.path)
            val profile: AppCompatImageView =
                binding.navDrawer.getHeaderView(0).findViewById(R.id.profilePic)
            profile.setImageBitmap(selectedImage)
        }
    }

    override fun initClick() {

        binding.navDrawer.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.roomDb -> openModule(R.id.roomFragment)
                R.id.fbLogin -> openModule(R.id.workFragment)
//                R.id.location -> openModule(R.id.broadCastFragment)
                R.id.logout -> LoginActivity.start(this@MainActivity)
            }
            true
        }

        binding.header.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.header.backBtn.setOnClickListener {
            navController.navigateUp()
        }

        binding.navDrawer.getHeaderView(0).findViewById<AppCompatImageView>(R.id.profilePic)
            .setOnClickListener {

                if (hasPermissions(camStoragePerm)) resultImageChooser.launch(
                    getPickImageChooserIntent()
                )
                else requestCameraPermission()
            }
    }

    override fun setVisibility(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    // region [ Runtime Permission ]

    private fun hasPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestCameraPermission() {

        when {
            rationalePermission(Manifest.permission.CAMERA) -> permissionsCompat(
                cameraPerm,
                Constant.CAMERA_PERMISSION_CODE
            )
            rationalePermission(Manifest.permission.READ_EXTERNAL_STORAGE) -> permissionsCompat(
                storagePerm,
                Constant.STORAGE_PERMISSION_CODE
            )
            else -> permissionsCompat(camStoragePerm, Constant.CAM_STORE_PER_CODE)
        }
    }

    private fun permissionsCompat(permissionsArray: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
    }

    private fun rationalePermission(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    private fun goToSettings() {

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Your application needs runtime permissions, please add manually")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
//            startActivityForResult(intent, Constant.SETTING_PERMISSION_CODE)
            askMultiplePermissions.launch(intent)
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialogBuilder.setCancelable(false)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private var askMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (hasPermissions(camStoragePerm)) {
                    resultImageChooser.launch(getPickImageChooserIntent())
                }
            }
        }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == Constant.SETTING_PERMISSION_CODE && hasPermissions(camStoragePerm)) {
//            resultImageChooser.launch(getPickImageChooserIntent())
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.i("onRequestPermissionsResult requestCode : $requestCode")

        for (i in permissions)
            Log.i("permissions : $i")

        when (requestCode) {
            Constant.CAM_STORE_PER_CODE -> {
                when {
                    hasPermissions(camStoragePerm) -> resultImageChooser.launch(
                        getPickImageChooserIntent()
                    )
                    !rationalePermission(Manifest.permission.READ_EXTERNAL_STORAGE) -> goToSettings()
                }
            }
            Constant.CAMERA_PERMISSION_CODE -> {
                when {
                    hasPermissions(cameraPerm) -> {
                        when {
                            hasPermissions(camStoragePerm) -> resultImageChooser.launch(
                                getPickImageChooserIntent()
                            )
                            else -> requestCameraPermission()
                        }
                    }
                }
            }
            Constant.STORAGE_PERMISSION_CODE -> {
                when {
                    hasPermissions(storagePerm) -> {
                        when {
                            hasPermissions(camStoragePerm) -> resultImageChooser.launch(
                                getPickImageChooserIntent()
                            )
                            else -> requestCameraPermission()
                        }
                    }
                }
            }
        }
    }

    // endregion

    // region [ File Chooser ]

    private fun getPickImageChooserIntent(): Intent? {

        val root = File(sdCardPath)
        if (!root.exists()) {
            root.mkdirs()
        }

        val allIntents = ArrayList<Intent>()
        val uriPic = FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".provider",
            createImageFile()
        )

        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            captureIntent.component =
                ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            captureIntent.setPackage(res.activityInfo.packageName)
            if (uriPic != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPic)
                captureIntent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            }
            allIntents.add(captureIntent)
        }

        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            galleryIntent.component =
                ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            galleryIntent.setPackage(res.activityInfo.packageName)
            allIntents.add(galleryIntent)
        }

        var mainIntent = allIntents[allIntents.size - 1]
        for (intent in allIntents) {
            if (intent.component?.className.equals("com.android.documentsui.DocumentsActivity")) {
                Log.i(Constant.TAG, "In remove Intent")
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)

        val chooserIntent = Intent.createChooser(mainIntent, "Select Source")
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            allIntents.toArray(arrayOfNulls<Parcelable>(allIntents.size))
        )

        return chooserIntent
    }

    private fun createImageFile(): File {
        return File("$sdCardPath/profile.jpg")
    }

    private var resultImageChooser =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val filePath = getImageFilePath(result.data)
                Log.i("File Path : $filePath")

                if (filePath != null) {
                    val selectedImage = BitmapFactory.decodeFile(filePath)
                    val profile: AppCompatImageView =
                        binding.navDrawer.getHeaderView(0).findViewById(R.id.profilePic)
                    profile.setImageBitmap(selectedImage)
                }
            }
        }

    private fun getImageFilePath(data: Intent?): String? {
        val isCamera = data?.data == null
        return if (isCamera) getCaptureImageOutputUri()!!.path
        else getPathFromURI(data?.data)
    }

    private fun getCaptureImageOutputUri(): Uri? {
        val getImage = File(sdCardPath, "profile.jpg")
        val outputFileUri = Uri.fromFile(getImage)

        Log.i(Constant.TAG, "outputFileUri : ${outputFileUri?.path}")

        return outputFileUri
    }

    private fun getPathFromURI(contentUri: Uri?): String? {
        val projection = arrayOf(MediaStore.Audio.Media._ID)
        val cursor = contentResolver.query(contentUri!!, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        cursor?.moveToFirst()
        val uri = cursor?.getString(columnIndex!!)
        cursor?.close()
        return uri
    }

    // endregion

    // region [ Navigation Drawer ]

    private fun openModule(fragment: Int) {
        closeDrawer()
        navController.popBackStack(R.id.menuFragment, false)
        navController.navigate(
            fragment, null,
            NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .build()
        )
    }

    private fun showHeader(label: String?) {

        binding.header.lblHeaderText.text = label

        if (label.equals("Menu")) {
            binding.header.backBtn.gone()
        } else {
            binding.header.backBtn.visible()
            setDrawerState(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeDrawer()
    }

    private fun setDrawerState(enable: Boolean) {
        binding.drawerLayout.setDrawerLockMode(if (enable) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    // endregion

}
