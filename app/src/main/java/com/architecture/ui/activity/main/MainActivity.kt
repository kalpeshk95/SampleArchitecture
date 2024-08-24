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
            context.startActivity(Intent(context, MainActivity::class.java))
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
    private lateinit var imageFilePath: String

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initClick()
    }

    override fun initView() {
        imageFilePath = getExternalFilesDir(null)?.absolutePath + "/SampleArch/profile.jpg"
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            showHeader(destination.label?.toString())
        }

        val imageFile = File(imageFilePath)
        if (imageFile.exists()) {
            displayImage(imageFile.toUri())
        }
    }

    override fun initClick() {
        with(binding) {
            header.backBtn.setOnClickListener {
                navController.navigateUp()
            }
            header.menu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.END)
            }
            navDrawer.getHeaderView(0)?.findViewById<AppCompatImageView>(R.id.profilePic)
                ?.setOnClickListener {
                    if (checkPermissions(camStoragePerm)) {
                        showImagePickerDialog()
                    } else {
                        requestCameraAndStoragePermissions()
                    }
                }
            navDrawer.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.roomDb -> openModule(R.id.roomFragment)
                    R.id.fbLogin -> openModule(R.id.workFragment)
                    R.id.logout -> LoginActivity.start(this@MainActivity)
                }
                true
            }
        }
    }

    override fun setVisibility(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    // region [ Runtime Permission ]
    private fun checkPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestCameraAndStoragePermissions() {
        if (shouldShowRationaleForPermissions()) {
            showPermissionsRationaleDialog()
        } else {
            goToSettings()
        }
    }

    private fun shouldShowRationaleForPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
        } else {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
        }
    }

    private fun showPermissionsRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permissions Required")
            .setMessage("This app needs camera and media permissions to function properly.")
            .setPositiveButton("Grant") { _, _ ->
                ActivityCompat.requestPermissions(this, camStoragePerm, Constant.CAM_STORE_PER_CODE)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun goToSettings() {
        AlertDialog.Builder(this)
            .setMessage("Your application needs runtime permissions, please add manually")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                askMultiplePermissions.launch(intent)
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()
            .show()
    }

    private val askMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (checkPermissions(camStoragePerm)) {
                    showImagePickerDialog()
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
        permissions.forEach { Timber.i("permissions : $it") }

        if (requestCode == Constant.CAM_STORE_PER_CODE && grantResults.isNotEmpty() &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            showImagePickerDialog()
        } else {
            requestCameraAndStoragePermissions()
        }
    }
    // endregion

    // region [ File Chooser ]
    private fun showImagePickerDialog() {
        AlertDialog.Builder(this)
            .setTitle("Select Image")
            .setItems(arrayOf("Take Picture", "Choose from Gallery")) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        imageUri = createImageUri()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
        cameraResult.launch(intent)
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri?.let { displayImage(it) }
            }
        }

    private fun createImageUri(): Uri? {
        val imageFile = File(imageFilePath)
        return FileProvider.getUriForFile(this, "${packageName}.provider", imageFile)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryResult.launch(intent)
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { copyImageToSDCard(it) }
            }
        }

    private fun copyImageToSDCard(imageUri: Uri) {
        try {
            displayImage(imageUri)
            contentResolver.openInputStream(imageUri)?.use { inputStream ->
                FileOutputStream(File(imageFilePath)).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            binding.root.showSnackBar("Failed to save image", "OK") {}
        }
    }

    private fun displayImage(uri: Uri) {
        binding.navDrawer.getHeaderView(0)?.findViewById<AppCompatImageView>(R.id.profilePic)
            ?.setImageURI(uri)
    }
    // endregion

    // region [ Navigation Drawer ]
    private fun openModule(fragment: Int) {
        binding.drawerLayout.closeDrawers()
        navController.popBackStack(R.id.menuFragment, false)
        navController.navigate(
            fragment, null,
            NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .build()
        )
    }

    private fun showHeader(label: String?) {
        with(binding.header) {
            lblHeaderText.text = label
            if (label == "Menu") {
                backBtn.gone()
            } else {
                backBtn.visible()
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.drawerLayout.closeDrawers()
    }
    // endregion
}
