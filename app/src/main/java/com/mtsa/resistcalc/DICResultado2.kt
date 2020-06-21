package com.mtsa.resistcalc

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.mtsa.fragments.FragDICAnalise
import com.mtsa.fragments.FragDICGraficos
import com.mtsa.fragments.FragDICResumo
import com.mtsa.resistcalc.databinding.ActDicResultadoBinding
import com.mtsa.utils.ViewPagerFragmentAdapter


class DICResultado2 : AppCompatActivity(), FragDICResumo.FragmentInterface, FragDICAnalise.FragmentInterface, FragDICGraficos.FragmentInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__resultado)
        val b = ActDicResultadoBinding.inflate(layoutInflater)
        setContentView(b.root)

        val adapter = ViewPagerFragmentAdapter(supportFragmentManager)
        b.viewPager.adapter = adapter
        b.tabLayout.setupWithViewPager(b.viewPager)
        setCustomFont(b.tabLayout)
    }

    override fun sendToActivity(s: String?) {
        // Código que interague com outros componentes, inclusive Fragments
        println("onItemSelected() -> $s")
    }

    override fun getFromActivity(): String {
        return "-------------------------- MESSAGE FROM ACTIVITY"
    }

    // AS INTERFACES DOS 3 FRAGMENTOS TEM ESSE MESMO MÉTODO IDÊNTICO
    // SERÁ QUE ISSO FUNCIONA PARA OS 3??
    override fun getDICFromActivity(): DIC2 {
        return intent.getSerializableExtra("DIC") as DIC2
    }

    private fun setCustomFont(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild: View = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    tabViewChild.typeface = ResourcesCompat.getFont(applicationContext, R.font.open_sans_bold)
                }
            }
        }
    }
}
