package com.architecture.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.architecture.R
import com.architecture.databinding.ActivityLoginBinding
import com.architecture.ui.activity.base.BaseActivity
import com.architecture.ui.activity.main.MainActivity

class LoginActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//        binding = setLayout(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initClick()
        observer()
    }

    override fun initView() {
        binding.edtUserName.setText(viewModel.getUserName())
        binding.edtPassword.setText(viewModel.getPassword())
    }

    override fun initClick() {
        binding.lblForgetPassword.setOnClickListener {
            viewModel.onForgetPassWd()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.onLoginClicked(
                binding.edtUserName.text.toString(),
                binding.edtPassword.text.toString()
            )
        }
    }

    private fun observer() {
        viewModel.toastMsg.observe(this, Observer {
            showToast(it)
        })

        viewModel.login.observe(this, Observer {
            if (it) {
                MainActivity.start(this@LoginActivity)
                viewModel.login.value = false
            }
        })
    }
}
