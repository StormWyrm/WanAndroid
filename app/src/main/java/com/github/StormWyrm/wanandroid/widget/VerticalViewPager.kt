package com.github.StormWyrm.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class VerticalViewPager : ViewPager {

    constructor(context: Context) : this(context, null)

    constructor (context: Context, attrs: AttributeSet?) : super(context, attrs)

    private fun swapTouchEvent(event: MotionEvent): MotionEvent {
        val width = width
        val height = height

        val swappedX = event.y / height * width
        val swappedY = event.x / width * height

        event.setLocation(swappedX, swappedY)

        return event
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
//        swapTouchEvent(event)
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
//        super.onTouchEvent(swapTouchEvent(ev))
        return false
    }
}