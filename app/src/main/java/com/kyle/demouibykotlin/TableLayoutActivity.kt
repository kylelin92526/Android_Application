package com.kyle.demouibykotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class TableLayoutActivity : AppCompatActivity() {
    var mTabs:com.google.android.material.tabs.TabLayout?=null
    var mViewPager: androidx.viewpager.widget.ViewPager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_layout)

        mTabs = findViewById(R.id.tabs)
        mTabs?.addTab(mTabs?.newTab()!!.setText("Tab 1"))
        mTabs?.addTab(mTabs?.newTab()!!.setText("Tab 2"))
        mTabs?.addTab(mTabs?.newTab()!!.setText("Tab 3"))

        mViewPager = findViewById<View>(R.id.viewpager) as ViewPager
        mViewPager!!.adapter = SamplePagerAdapter()
        mViewPager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(mTabs))

        mTabs?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))
        /*mTabs?.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("actttt","1="+tab?.text)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //Log.d("kyle","2="+tab?.text)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //Log.d("kyle","3="+tab?.text)
            }

        })*/

    }

    internal class SamplePagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return 3
        }

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return o === view
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return (position + 1).toString()
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context).inflate(R.layout.pager_item, container, false)
            container.addView(view)
            val title = view.findViewById(R.id.item_title) as TextView
            title.text = getPageTitle(position)
            Log.d("actttt instantiateItem","position+1="+title.text)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            Log.d("actttt destroyItem","destroyItem")
            container.removeView(`object` as View)
        }
    }
    /*
    运行时，当我进入第一页时，log打印 0和1，当我滑到第二页时，log打印2，当我滑到第三页时，log没有输出信息。
    所以我觉得是这样的。因为pagerAdapter是默认预加载前后一张的，所以当加载第一页时。
    调用了两次instantiateItem方法，第一次是加载本来的第一页，第二次是预加载第二页，所以log打印了0和1。
    可是显示的本来加载的第一页。当滑动到第二页时，只调用了一次instantiateItem方法，
    是因为本页已经在之前预加载过了，没有再调用instantiateItem方法也就是log不会再打印1，
    而是直接从ViewGroup中直接取出第二页用于显示。然后进行预加载第三页，所以这里会调用instantiateItem方法并打印2.
    接着滑动到第三页，由于第三节页也已经预加载过了，所以只是从ViewGroup取出第三页显示而不调用instantiateItem。
    但是由于预加载默认是前后一张，所以这时会从ViewGroup中取出第一页销毁。
    直到从第三页滑到第二页时才会再预加载第一页
    */


}