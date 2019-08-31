package com.example.looquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.act_notice.*

class NoticeActivity : AppCompatActivity() {

    var view_list = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_notice)

        view_list.add(layoutInflater.inflate(R.layout.pager_notice, null))
        view_list.add(layoutInflater.inflate(R.layout.pager_faq, null))

        pager_notice.adapter = CustomAdapter()
        tab_notice.setupWithViewPager(pager_notice)

        //익명중첩클래스 사용
        pager_notice.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageSelected(p0: Int) {


            }
        })

    }

    inner class CustomAdapter: PagerAdapter(){
        override fun getCount(): Int {
            return view_list.size
        }

        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            //현재 객체가 보여줄 객체와 일치하는지 구분
            return p0 == p1
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            pager_notice.addView(view_list[position])

            return view_list[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            pager_notice.removeView(obj as View) //obj를 View로 형변환 후 제거
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0 -> return "공지사항"
                1 -> return "자주 묻는 질문"
            }
            return null
        }
    }
}
