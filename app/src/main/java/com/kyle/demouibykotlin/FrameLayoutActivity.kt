package com.kyle.demouibykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

/*
 FrameLayout 就是一層蓋一層
 */

class FrameLayoutActivity : AppCompatActivity() {
    //lateinit var btn0:Button
    //lateinit var btn1:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_layout)

    }

    fun fritst(view: View) {
      Log.d("actttt","frist")
    }

    fun second(view: View) {
        Log.d("actttt","second")
    }
}