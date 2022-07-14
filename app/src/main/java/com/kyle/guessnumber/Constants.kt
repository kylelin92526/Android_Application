package com.kyle.guessnumber

import androidx.appcompat.app.AppCompatActivity

/*
 *@author Kyle Lin
 *@data 2022/5/23
 */class Constants {
    companion object {
        val DATA_FILE = "GuessNumberData"
        val KEY_GAME_SETTINGS = "KEY_GAME_SETTINGS"
        val KEY_TIME_SETTINGS = "KEY_TIME_SETTINGS"
        val KEY_GUIDE_LINE = "KEY_GUIDE_LINE"
        val DEFAULT_VALUE = 15
        var gameSettings: Int = DEFAULT_VALUE
        var timeSettings: Int = DEFAULT_VALUE
        var KEY_SELECT_VIEW = "KEY_SELECT_VIEW"
    }

}