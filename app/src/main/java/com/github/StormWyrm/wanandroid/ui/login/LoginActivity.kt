package com.github.StormWyrm.wanandroid.ui.login

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpActivity
import com.github.StormWyrm.wanandroid.bean.LoginBean
import com.github.StormWyrm.wanandroid.sendEvent
import com.github.StormWyrm.wanandroid.ui.register.RegisterActivity
import com.github.StormWyrm.wanandroid.utils.UserUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(),
    LoginContract.View {
    override var mPresenter: LoginContract.Presenter = LoginPresenter()
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        super.initView()
        initToolbar(getString(R.string.login_title))

        tvRegister.paint.flags = Paint.UNDERLINE_TEXT_FLAG//添加下划线
    }

    override fun initLisenter() {
        super.initLisenter()
        etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val errorText = tilUsername.error
                if (!errorText.isNullOrEmpty() && count > 0) {
                    tilUsername.isErrorEnabled = false
                }
            }

        })
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val errorText = tilPassword.error
                if (!errorText.isNullOrEmpty() && count > 0) {
                    tilPassword.isErrorEnabled = false
                }
            }

        })
        tvLogin.setOnClickListener {
            val username = etUsername.text?.trim().toString()
            if (username.isNullOrEmpty()) {
                tilUsername.isErrorEnabled = true
                tilUsername.error = getString(R.string.login_username_empty)
                return@setOnClickListener
            }
            val password = etPassword.text?.trim().toString()
            if (password.isNullOrEmpty()) {
                tilPassword.isErrorEnabled = true
                tilPassword.error = getString(R.string.login_password_empty)
                return@setOnClickListener
            }
            val params = hashMapOf("username" to username, "password" to password)
            mPresenter.requestLogin(params)
        }

        tvRegister.setOnClickListener {
            RegisterActivity.start(mActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_REGISTER_CODE && resultCode == Activity.RESULT_OK) {
            val username = intent.getStringExtra("username")
            val password = intent.getStringExtra("password")
            if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
                etUsername.setText(username)
                etPassword.setText(password)
            }
        }
    }

    override fun onLoginSuccess(loginBean: LoginBean) {
        UserUtils.saveUsername(loginBean.username)
        sendEvent(loginBean)
        finish()
    }

    companion object {
        const val REQUEST_REGISTER_CODE = 0x00000001

        fun start(context: BaseActivity) {
            val intent = Intent().apply {
                setClass(context, LoginActivity::class.java)
            }
            context.startActivity(intent)
        }
    }
}