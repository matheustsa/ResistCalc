package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mtsa.fragments.FragDICAnalise
import com.mtsa.fragments.FragDICGraficos
import com.mtsa.fragments.FragDICResumo
import com.mtsa.resistcalc.databinding.ActDicResultadoAmostrasBinding
import com.mtsa.resistcalc.databinding.ActDicResultadoBinding
import com.mtsa.resistcalc.databinding.FragDicGraficosBinding
import com.mtsa.utils.Utils
import com.mtsa.utils.ViewPagerFragmentAdapter
import kotlinx.android.synthetic.main.frag_dic__analise.view.*
import org.apache.commons.math3.distribution.FDistribution
import kotlin.math.pow


class DICResultado2 : AppCompatActivity(), FragDICResumo.FragmentInterface, FragDICAnalise.FragmentInterface, FragDICGraficos.FragmentInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__resultado)
        val b = ActDicResultadoBinding.inflate(layoutInflater)
        setContentView(b.root)

        val adapter = ViewPagerFragmentAdapter(supportFragmentManager)
        b.viewPager.adapter = adapter
        b.tabLayout.setupWithViewPager(b.viewPager)
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
}
