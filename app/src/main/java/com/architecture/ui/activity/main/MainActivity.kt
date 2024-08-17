package com.architecture.ui.activity.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.architecture.R
import com.architecture.databinding.ActivityMainBinding
import com.architecture.ex.gone
import com.architecture.ex.showSnackBar
import com.architecture.ex.visible
import com.architecture.ui.activity.base.BaseActivity
import com.architecture.ui.activity.login.LoginActivity
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.utils.Constant
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

class MainActivity : BaseActivity(), BaseFragment.ShowProgressBar {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val cameraPerm = arrayOf(Manifest.permission.CAMERA)
    private val storagePerm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
    private val camStoragePerm = cameraPerm + storagePerm

    private var imageUri: Uri? = null
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
            displayImage(root.toUri())
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
                if (checkPermissions(camStoragePerm)) {
                    showFileChooserDialog()
                } else {
                    permissionsCompat(camStoragePerm, Constant.CAM_STORE_PER_CODE)
                }
            }
    }

    override fun setVisibility(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    // region [ Runtime Permission ]

    private fun checkPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermissionsWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.READ_MEDIA_IMAGES
            )
        ) {
            // Show rationale and re-request permissions
            AlertDialog.Builder(this)
                .setTitle("Permissions Required")
                .setMessage("This app needs camera and media permissions to function properly.")
                .setPositiveButton("Grant") { _, _ ->
                    permissionsCompat(camStoragePerm, Constant.CAM_STORE_PER_CODE)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        } else {
            goToSettings()
        }
    }

    private fun permissionsCompat(permissionsArray: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
    }

    private fun goToSettings() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Your application needs runtime permissions, please add manually")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
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
                if (checkPermissions(camStoragePerm)) {
                    showFileChooserDialog()
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.i("onRequestPermissionsResult requestCode : $requestCode")
        for (i in permissions)
            Timber.i("permissions : $i")

        when (requestCode) {
            Constant.CAM_STORE_PER_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Permissions granted, proceed with the camera or media access
                    showFileChooserDialog()
                } else {
                    // Permissions denied, handle accordingly
                    requestPermissionsWithRationale()
                }
            }
        }
    }

    // endregion

    // region [ File Chooser ]

    private fun showFileChooserDialog() {
        val options = arrayOf("Take Picture", "Choose from Gallery")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Image")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageUri = createImageUri() // Create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraResult.launch(intent)
    }

    private var cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.i("Camera Image Uri : ${imageUri?.path}")
                imageUri?.let { displayImage(it) }
            }
        }

    private fun createImageUri(): Uri? {
        return FileProvider.getUriForFile(this, "${packageName}.provider", createImageFile())
    }

    private fun createImageFile(): File {
        return File("$sdCardPath/profile.jpg")
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResult.launch(intent)
    }

    private var galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.i("Gallery Image Uri : ${result.data?.data}")
                result?.data?.data?.let { copyImageToSDCard(it) }
            }
        }

    private fun copyImageToSDCard(imageUri: Uri) {
        try {
            displayImage(imageUri)
            // Open input and output streams
            val inputStream = contentResolver.openInputStream(imageUri)
            val outputStream = FileOutputStream(createImageFile())

            // Copy the data from input to output
            inputStream?.copyTo(outputStream)

            // Close the streams
            inputStream?.close()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.root.showSnackBar("Failed to save image", "OK") {}
        }
    }


    private fun displayImage(uri: Uri) {
        val profile: AppCompatImageView =
            binding.navDrawer.getHeaderView(0).findViewById(R.id.profilePic)
        profile.setImageURI(uri)
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
            setDrawerState()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeDrawer()
    }

    private fun setDrawerState() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    // endregion

}
