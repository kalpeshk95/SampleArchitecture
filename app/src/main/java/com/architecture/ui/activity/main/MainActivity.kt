package com.architecture.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.architecture.R
import com.architecture.databinding.ActivityMainBinding
import com.architecture.ui.activity.base.BaseActivity
import com.architecture.ui.activity.login.LoginActivity
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : BaseActivity() {

    companion object{
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

//    private val model by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = setLayout(R.layout.activity_main)

        initView()
        initClick()
    }

    override fun initView() {

        navController = Navigation.findNavController(this, R.id.fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            showHeader(destination.label?.toString())
        }
    }

    override fun initClick() {

        binding.navDrawer.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.roomDb -> openModule(R.id.roomFragment)
                R.id.fbLogin -> openModule(R.id.workFragment)
//                R.id.location -> openModule(R.id.location)
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

//        binding.drawerLayout.profilePic.setOnClickListener {
//            if (hasPermissions(allPermission)) startActivityForResult(getPickImageChooserIntent(), imgResult)
//            else requestCameraPermission()
//        }
    }

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

    private fun setDrawerState(enable: Boolean) {
        binding.drawerLayout.setDrawerLockMode(if (enable) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }
}
