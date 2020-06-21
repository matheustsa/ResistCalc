package com.mtsa.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mtsa.resistcalc.DIC2
import com.mtsa.resistcalc.R
import com.mtsa.resistcalc.databinding.FragDicResumoBinding
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.firstChild
import org.jetbrains.anko.internals.AnkoInternals.applyRecursively

class FragDICResumo : Fragment() {

    private lateinit var binding: FragDicResumoBinding
    private lateinit var dic: DIC2
    private lateinit var fragmentInterface: FragmentInterface

    //    DEFININDO INTERFACES DE COMUNICAÇÃO COM A ACTIVITY
    interface FragmentInterface {
        fun getDICFromActivity(): DIC2
    }

    private fun getDIC(): DIC2 {
        return fragmentInterface.getDICFromActivity()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInterface)
            fragmentInterface = context
        else
            throw ClassCastException()
    }
//    --------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragDicResumoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeTable()
        styleTable()
    }

    @SuppressLint("SetTextI18n")
    private fun makeTable(){
        dic = getDIC()

        for (i in dic.lista.indices) {
            val txvT = TextView(context)
            val txvMedia = TextView(context)
            val txvDesvP = TextView(context)
            val txvVaria = TextView(context)
            val txvCV = TextView(context)

            txvT.text = "T${i+1}"
            txvMedia.text = "${dic.media[i]}"
            txvDesvP.text = "${dic.desvioPadrao[i]}"
            txvVaria.text = "${dic.variancia[i]}"
            txvCV.text = "${dic.coeficienteVariacao[i]}"

            binding.trTratamentos.addView(txvT)
            binding.trMedia.addView(txvMedia)
            binding.trDesvio.addView(txvDesvP)
            binding.trVariancia.addView(txvVaria)
            binding.trCoefVariancia.addView(txvCV)

//            Média Global, k, n
            when (i) {
                0 -> {
                    val txvMG = TextView(context)
                    txvMG.text = "${dic.mediaGlobal}"
                    binding.trMediaGlobal.addView(txvMG)
                }
                /*
                1 -> {
                    val txvK = TextView(context)
                    txvK.text = "k"
                    binding.trMediaGlobal.addView(txvK)
                }
                2 -> {
                    val txvK = TextView(context)
                    txvK.text = "${dic.k}"
                    binding.trMediaGlobal.addView(txvK)
                }
                3 -> {
                    val txvN = TextView(context)
                    txvN.text = "n"
                    binding.trMediaGlobal.addView(txvN)
                }
                4 -> {
                    val txvN = TextView(context)
                    txvN.text = "${dic.n}"
                    binding.trMediaGlobal.addView(txvN)
                }

                 */
            }
        }

    }

    @SuppressLint("NewApi")
    private fun styleTable() {
        val b = binding
        val table = b.table

        for (i in 0..table.childCount) {
            val row = table.getChildAt(i) as? TableRow

            if (row != null) {
                for (j in 0..row.childCount) {
                    val child = row.getChildAt(j) as? TextView
                    child?.foreground = ContextCompat.getDrawable(context!!, R.drawable.table_square_transparent_white)
                    child?.setPadding(24,8,24,8)
                    child?.gravity = if (j != 0) Gravity.CENTER else Gravity.START
                    child?.setTextColor(Color.WHITE)
                    child?.setTextAppearance(R.style.table_dic_content_dark)

                    child?.background = if (j%2 == 0) ContextCompat.getDrawable(context!!, R.color.dic_blue_dark) else ContextCompat.getDrawable(context!!, R.color.dic_blue_light)
                }
                val child = row.getChildAt(0) as? TextView
                child?.setTextAppearance(R.style.table_dic_header_dark)
            }

        }

//        for (i in 0..table.childCount) {
//            val row = table.getChildAt(i) as? TableRow
//            val child = row?.getChildAt(0) as? TextView
//            child?.setTextAppearance(R.style.table_dic_header_dark)
//        }
    }


}