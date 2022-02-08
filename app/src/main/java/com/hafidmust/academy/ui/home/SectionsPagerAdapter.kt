package com.hafidmust.academy.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hafidmust.academy.R
import com.hafidmust.academy.ui.academy.AcademyFragment
import com.hafidmust.academy.ui.bookmark.BookmarkFragment

class SectionsPagerAdapter(private val mContext : Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AcademyFragment()
            1 -> BookmarkFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(
        TAB_TITLES[position])

    companion object{
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }

}