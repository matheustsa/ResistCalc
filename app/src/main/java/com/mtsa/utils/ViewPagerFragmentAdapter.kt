package com.mtsa.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mtsa.fragments.DICAmostras
import com.mtsa.fragments.DICGraficos
import com.mtsa.fragments.DICResumo

class ViewPagerFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // tab titles
    private val tabTitles =
        arrayOf("Resumo", "Análise", "Gráficos")

    // overriding getPageTitle()
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }


    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DICResumo()
            1 -> DICAmostras()
            2 -> DICGraficos()
            else -> null
        }!!
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return tabTitles.count()
    }
}