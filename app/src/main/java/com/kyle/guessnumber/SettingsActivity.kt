package com.kyle.guessnumber

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    var linearlayoutNumber = arrayOf(R.id.linearlayout0,R.id.linearlayout1,R.id.linearlayout2)
    var linearlayoutView = arrayOfNulls<LinearLayout>(3)
    var txtShowgame:TextView?=null
    var game_15:LinearLayout?=null
    var game_30:LinearLayout?=null
    var game_45:LinearLayout?=null
    var game_60:LinearLayout?=null

    var txtShowtime:TextView?=null
    var time_15:LinearLayout?=null
    var time_30:LinearLayout?=null
    var time_45:LinearLayout?=null
    var time_60:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        PreferenceFinder.getInstance(this)
        txtShowgame = findViewById(R.id.textshowgame)
        txtShowgame?.text = Constants.gameSettings.toString()
        game_15 = findViewById(R.id.game_15)
        game_30 = findViewById(R.id.game_30)
        game_45 = findViewById(R.id.game_45)
        game_60 = findViewById(R.id.game_60)

        txtShowtime = findViewById(R.id.textshowtime)
        txtShowtime?.text = Constants.timeSettings.toString()
        time_15 = findViewById(R.id.time_15)
        time_30 = findViewById(R.id.time_30)
        time_45 = findViewById(R.id.time_45)
        time_60 = findViewById(R.id.time_60)

        var j = 0
        for(i in linearlayoutNumber){
            linearlayoutView[j]=findViewById(i)
            if(j<linearlayoutNumber.size) j++
        }
        linearlayoutView[0]?.let { setLinearLayoutAnimation(it,1000) }
        linearlayoutView[1]?.let { setLinearLayoutAnimation(it,2000) }
        linearlayoutView[2]?.let { setLinearLayoutAnimation(it,3500) }

    }

    fun setGame(view: View) {
        when (view) {
            game_15 -> {
                Constants.gameSettings = 15
            }
            game_30 -> {
                Constants.gameSettings = 30
            }
            game_45 -> {
                Constants.gameSettings = 45
            }
            game_60 -> {
                Constants.gameSettings = 60
            }
        }
        PreferenceFinder.setPrefsIntValue(Constants.KEY_GAME_SETTINGS,Constants.gameSettings)
        txtShowgame?.text = Constants.gameSettings.toString()
    }

    fun setTime(view: View) {
        when (view) {
            time_15 -> {
                Constants.timeSettings = 15
            }
            time_30 -> {
                Constants.timeSettings = 30
            }
            time_45 -> {
                Constants.timeSettings = 45
            }
            time_60 -> {
                Constants.timeSettings = 60
            }
        }
        PreferenceFinder.setPrefsIntValue(Constants.KEY_TIME_SETTINGS,Constants.timeSettings)
        txtShowtime?.text = Constants.timeSettings.toString()
    }

    private fun setLinearLayoutAnimation(view: View, time: Int) {
        val showAnim = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f
        )
        showAnim.duration = time.toLong()
        view.startAnimation(showAnim)
        view.visibility = View.VISIBLE
    }

    fun gotoGuideActivity(view: View) {
        /*
         設置一個儲存值 , 讓導覽頁面可分辨要進入遊戲或返回設置
         */
        PreferenceFinder.setPrefsIntValue(Constants.KEY_SELECT_VIEW,1)
        val intent = Intent(this, GuideActivity::class.java)
        startActivity(intent)
        finish()
    }
}