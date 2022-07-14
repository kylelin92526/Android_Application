package com.kyle.guessnumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.kyle.guessnumber.view.GuessNumberViewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceFinder.getInstance(this)
        /*
          每次開起遊戲都需要讀取一次設定檔
         */
        Constants.gameSettings = PreferenceFinder.getPrefsIntValue(Constants.KEY_GAME_SETTINGS)!!
        Constants.timeSettings = PreferenceFinder.getPrefsIntValue(Constants.KEY_TIME_SETTINGS)!!

        /*
          判斷是否第一次開啟遊戲 , 來啟動導覽頁面
         */
        val data = PreferenceFinder.getPrefsIntValue(Constants.KEY_GUIDE_LINE)
        if(data == 1){
            gotoActivity(GuessNumberViewActivity::class.java)
            finish()
        }else{
            gotoActivity(GuideActivity::class.java)
            finish()
        }
    }

    private fun gotoActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}