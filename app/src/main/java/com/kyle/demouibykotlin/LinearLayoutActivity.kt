package com.kyle.demouibykotlin

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LinearLayoutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        /*
        在 linearlayout id linear2 增加 一個

        LinearLayout 裡面有 一個 textview 跟 一個 button
        1.先 new 一個 LinearLayout (llay)
        2.設置 LinearLayout 的 params
        3.new and 設置 textview 跟 button , 並 llay.addview
        4.最後導入 xml 設置好的 id linear2


         */

        val llay = LinearLayout(this)
        llay.orientation = LinearLayout.VERTICAL
        llay.setBackgroundColor(Color.BLUE)


        val llp: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 200)
        llp.weight = 1.0f
        llp.gravity = 5

        var txt = TextView(this)
        txt.text="1234567"
        txt.layoutParams = llp
        txt.gravity = 5

        var btn = Button(this)
        btn.text = "btn1"
        btn.layoutParams = llp
        btn.gravity = 5

        btn.setOnClickListener{
            txt.text = "56789"
        }

        llay.addView(txt)
        llay.addView(btn)


        var linear2:LinearLayout?=null
        linear2 = findViewById(R.id.linear2)
        linear2.addView(llay)



    }
}