package com.architecture.ui.activity.splash

import android.os.Bundle
import com.architecture.R
import com.architecture.ui.activity.base.BaseActivity
import com.architecture.ui.activity.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initView()
        initClick()

//        Handler().postDelayed(Runnable {
//            // This method will be executed once the timer is over
//            val i = Intent(this@SplashActivity, LoginActivity::class.java)
//            startActivity(i)
//            finish()
//        }, 5000)
    }

    override fun initView() {

        launch(Dispatchers.Main) {
//            delay(5000L)
            LoginActivity.start(this@SplashActivity)
            this@SplashActivity.finish()
        }
    }

    override fun initClick() {

    }
}
