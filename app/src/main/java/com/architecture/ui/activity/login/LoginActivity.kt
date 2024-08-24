package com.architecture.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.architecture.R
import com.architecture.databinding.ActivityLoginBinding
import com.architecture.ui.activity.base.BaseActivity
import com.architecture.ui.activity.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    companion object {
        fun start(context: Context) = context.startActivity(
            Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        )
    }

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initClick()
        observer()
    }

    override fun initView() {
        with(binding) {
            edtUserName.setText(viewModel.getUserName())
            edtPassword.setText(viewModel.getPassword())
        }
    }

    override fun initClick() {
        with(binding) {
            lblForgetPassword.setOnClickListener { viewModel.onForgetPassWd() }

            btnLogin.setOnClickListener {
                viewModel.onLoginClicked(
                    edtUserName.text.toString(),
                    edtPassword.text.toString()
                )
            }
        }
    }

    private fun observer() {
        with(viewModel) {
            toastMsg.observe(this@LoginActivity) { showToast(it) }
            login.observe(this@LoginActivity) {
                if (it) {
                    MainActivity.start(this@LoginActivity)
                    setLogin()
                }
            }
        }
    }
}