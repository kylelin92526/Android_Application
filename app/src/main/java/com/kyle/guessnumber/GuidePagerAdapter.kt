package com.kyle.guessnumber

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter


/*
 *@author Kyle Lin
 *@data 2022/5/26
 */
class GuidePagerAdapter: PagerAdapter {
    private var pageview1: ArrayList<View>? = null

    constructor(pageview1: ArrayList<View>?){
        this.pageview1 = pageview1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (pageview1?.get(position)!=null) {
            container.removeView(pageview1?.get(position));
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        container.addView(pageview1!![position])
        Log.d("MainActivityInstanti", position.toString() + "")
        return pageview1!![position]!!
    }

    override fun getCount(): Int {
        return pageview1!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }}