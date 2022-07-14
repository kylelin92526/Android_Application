package com.kyle.guessnumber

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.kyle.guessnumber.view.GuessNumberViewActivity


class GuideActivity : AppCompatActivity() {
    private var viewPager: ViewPager? = null

    //5個view
    private var view1: View? = null
    private var view2: View? = null
    private var view3: View? = null
    private var view4: View? = null
    private var view5: View? = null

    companion object {
        //用來存放view並傳遞給viewPager的介面卡。
        var pageview: ArrayList<View>? = null
        //用來存放圓點,定義一下5個變量了。
        var tips: Array<ImageView?> = arrayOfNulls<ImageView>(5)
    }
    var imageView: ImageView? = null

    //圓點組的物件
    private var group: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        viewPager = findViewById<View>(R.id.viewPager) as ViewPager

        view1 = layoutInflater.inflate(R.layout.guide_page1, null)
        view2 = layoutInflater.inflate(R.layout.guide_page2, null)
        view3 = layoutInflater.inflate(R.layout.guide_page3, null)
        view4 = layoutInflater.inflate(R.layout.guide_page4, null)
        view5 = layoutInflater.inflate(R.layout.guide_page5, null)

        pageview = ArrayList()
        pageview?.add(view1!!)
        pageview?.add(view2!!)
        pageview?.add(view3!!)
        pageview?.add(view4!!)
        pageview?.add(view5!!)

        //viewPager下面的圓點，ViewGroup
        group = findViewById<View>(R.id.viewGroup) as ViewGroup
        tips = pageview?.size?.let { arrayOfNulls(it) }!!
        for (i in pageview?.indices!!) {
            imageView = ImageView(this@GuideActivity)
            imageView?.layoutParams = ViewGroup.LayoutParams(60, 60)
            imageView?.setPadding(20, 0, 20, 0)
            tips[i] = imageView

            //預設第一張圖顯示為選中狀態
            if (i == 0) {
                tips[i]!!.setBackgroundResource(R.mipmap.page_indicator_focused)
            } else {
                tips[i]!!.setBackgroundResource(R.mipmap.page_indicator_unfocused)
            }
            group?.addView(tips[i])
        }


        viewPager?.adapter = GuidePagerAdapter(pageview)
        viewPager?.addOnPageChangeListener(GuidePageChangeListener())

        /*
         第五頁 設置 點擊畫面進入遊戲或返回設置
         */
        var layout = view5?.findViewById<RelativeLayout>(R.id.frist_layout)
        layout?.setOnClickListener {
            var selectViewValue = PreferenceFinder.getPrefsIntValue(Constants.KEY_SELECT_VIEW)!!
            if(selectViewValue == 1){
                PreferenceFinder.setPrefsIntValue(Constants.KEY_SELECT_VIEW,Constants.DEFAULT_VALUE)
                var a = Intent(this, SettingsActivity::class.java)
                startActivity(a)
                finish()
            }else{
                PreferenceFinder.setPrefsIntValue(Constants.KEY_GUIDE_LINE,1)
                var a = Intent(this, GuessNumberViewActivity::class.java)
                startActivity(a)
                finish()
            }

        }
    }

    internal class GuidePageChangeListener : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageScrollStateChanged(state: Int) {}

        //切換view時，下方圓點的變化。
        override fun onPageSelected(position: Int) {
            tips?.get(position)?.setBackgroundResource(R.mipmap.page_indicator_focused)
            //這個圖片就是選中的view的圓點
            for (i in pageview?.indices!!) {
                if (position != i) {
                    tips?.get(i)?.setBackgroundResource(R.mipmap.page_indicator_unfocused)
                    //這個圖片是未選中view的圓點
                }
            }
        }
    }

}