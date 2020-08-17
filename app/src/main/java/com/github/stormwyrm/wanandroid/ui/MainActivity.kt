package com.github.stormwyrm.wanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.stormwyrm.wanandroid.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val oneFragment = BlankFragment.newInstance("OneFragment", "")
        val newInstance = BlankFragment.newInstance("TwoFragment", "")
        val blankFragment = BlankFragment.newInstance("ThreeFragment", "")

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, oneFragment, "OneFragment")
            .add(R.id.flContainer, newInstance, "TwoFragment")
            .add(R.id.flContainer, blankFragment, "ThreeFragment")
            .hide(newInstance)
            .hide(blankFragment)
            .commit()
    }


}
