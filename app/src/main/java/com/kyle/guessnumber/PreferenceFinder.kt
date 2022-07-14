package com.kyle.guessnumber

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


/*
 *@author Kyle Lin
 *@data 2022/5/23
 */class PreferenceFinder {

    constructor(context: Context){
        mContext = context
    }

    companion object {
        private var mContext: Context? = null
        private var instance: PreferenceFinder? = null
        @Synchronized
        fun getInstance(context: Context): PreferenceFinder? {
            if (instance == null) {
                instance = PreferenceFinder(context)
            }
            return instance
        }

        fun setPrefsIntValue(key: String?, value: Int) {
            val pref = mContext?.getSharedPreferences(Constants.DATA_FILE, MODE_PRIVATE)
            pref?.edit()
                ?.putInt(key,value)
                ?.commit()
        }


        fun getPrefsIntValue(key: String?): Int? {
            val pref = mContext?.getSharedPreferences(Constants.DATA_FILE, MODE_PRIVATE)
            return pref?.getInt(key,Constants.DEFAULT_VALUE)
        }
    }


}