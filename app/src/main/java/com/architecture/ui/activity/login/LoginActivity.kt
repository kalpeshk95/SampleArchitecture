package com.architecture.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.architecture.R
import com.architecture.databinding.ActivityLoginBinding
import com.architecture.ui.activity.main.MainActivity
import com.architecture.ui.activity.base.BaseActivity

class LoginActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

    private val model by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java) }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = setLayout(R.layout.activity_login)

        initView()
        initClick()
    }

    override fun initView() {
        binding.model = model
        model.setPrefValue()

        model.toastMsg.observe(this) {
            showToast(it)
        }

//        model.liveDataMerger.observe(this,
//            Observer {
//                if (it) {
//                    MainActivity.start(this@LoginActivity)
//                    model.liveDataMerger.removeSource(model.login)
//                }
//            })

        model.login.observe(this) {
            if (it) {
                MainActivity.start(this@LoginActivity)
                model.login.value = false
            }
        }
    }

    override fun initClick() {

    }
}
