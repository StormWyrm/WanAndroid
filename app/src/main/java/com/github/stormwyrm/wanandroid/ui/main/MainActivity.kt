package com.github.stormwyrm.wanandroid.ui.main

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initLisenter() {
        super.initLisenter()
        btnOpen.setOnClickListener {
            try {
                val uri =
                    Uri.parse("market://details?id=com.google.android.apps.photos")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

}
