package com.github.StormWyrm.wanandroid.ui.register

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpActivity
import com.github.StormWyrm.wanandroid.bean.RegisterBean
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(),
    RegisterContract.View {
    override var mPresenter: RegisterContract.Presenter = RegisterPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        super.initView()
        initToolbar(getString(R.string.register_title))
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
        etRepassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val errorText = tilRepassword.error
                if (!errorText.isNullOrEmpty() && count > 0) {
                    tilRepassword.isErrorEnabled = false
                }
            }

        })
        tvRegister.setOnClickListener {
            val username = etUsername.text?.trim().toString()
            if (username.isNullOrEmpty()) {
                tilUsername.isErrorEnabled = true
                tilUsername.error = getString(R.string.login_username_empty)
                return@setOnClickListener
            }
            val password = etPassword.text?.trim().toString()
            if (password.isNullOrEmpty()) {
                tilUsername.isErrorEnabled = true
                tilPassword.error = getString(R.string.login_password_empty)
                return@setOnClickListener
            }
            val repassword = etRepassword.text?.trim().toString()
            if (repassword.isNullOrEmpty()) {
                tilRepassword.isErrorEnabled = true
                tilRepassword.error = getString(R.string.login_repassword_empty)
                return@setOnClickListener
            }
            if (repassword != password) {
                tilRepassword.isErrorEnabled = true
                tilRepassword.error = getString(R.string.register_inconsistent_password)
                return@setOnClickListener
            }
            val params = hashMapOf("username" to username, "password" to password, "repassword" to repassword)
            mPresenter.requestRegister(params)
        }
    }

    override fun onRegisterSuccess(registerBean: RegisterBean) {
        AlertDialog.Builder(mActivity)
            .setTitle(getString(R.string.dialog_hit))
            .setMessage(getString(R.string.dialog_register_success))
            .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, _: Int ->
                val intent = Intent().apply {
                    putExtra("username", registerBean.username)
                    putExtra("password", registerBean.password)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, _: Int ->
                finish()
                dialogInterface.dismiss()
            }.show()
    }

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent().apply {
                setClass(context, RegisterActivity::class.java)
            }
            context.startActivity(intent)
        }
    }
}
