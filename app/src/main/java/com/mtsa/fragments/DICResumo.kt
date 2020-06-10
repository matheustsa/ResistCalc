package com.mtsa.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.mtsa.resistcalc.DIC
import com.mtsa.resistcalc.databinding.FragDicResumoBinding
import com.mtsa.utils.Utils
import org.apache.commons.math3.distribution.FDistribution
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import kotlin.math.pow

/*
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DICResumo.newInstance] factory method to
 * create an instance of this fragment.
 */

 */
class DICResumo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dic = arguments?.getSerializable("DIC") as DIC
        println(dic)

        val b = FragDicResumoBinding.inflate(layoutInflater, container, false)

        calcularDIC(dic, b)

        return b.root

//        return inflater.inflate(R.layout.frag_dic__resumo, container, false)
    }

    @SuppressLint("SetTextI18n")
    private fun calcularDIC(
        dic: DIC,
        b: FragDicResumoBinding
    ) {
//        val dic = intent.getSerializableExtra("DIC") as DIC

        val k = dic.k
        val n = dic.n
        val alfa = dic.alfa
        val lista = dic.lista

        val gl1 = k-1
        val gl2 = k*(n-1)
        val glTotal = gl1+gl2

        val media = mutableListOf<Double>()
        val desvioPadrao = mutableListOf<Double>()
        val variancia = mutableListOf<Double>()
        val coeficienteVariacao = mutableListOf<Double>()

        for ((index, T) in lista.withIndex()) {
            media.add(Utils.roundDec(T.average(), 4))
            desvioPadrao.add(Utils.desvioPadrao(T))
            variancia.add(Utils.variancia(T))
            coeficienteVariacao.add(Utils.coefVariacao(media[index], desvioPadrao[index]))
        }

        val mediaGlobal = Utils.roundDec((lista.sumByDouble { it.sum() }/(k*n)), 4)
//        println("=================== MEDIA GLOBAL:" + lista.sumByDouble { it.sum() })

        val QM_Dentro = Utils.roundDec(variancia.sum() / k, 4)
//       N * (m0 - m_global)^2 + (m1 - m_global)^2 + ...

        var QM_Entre = 0.0
        for (value in media) {
            QM_Entre += (value - mediaGlobal).pow(2)
        }
        QM_Entre *= n
        QM_Entre = Utils.roundDec(QM_Entre, 4)

        val F_Calculado = Utils.roundDec(QM_Entre / QM_Dentro, 4)
        val F_Critico = Utils.roundDec(FDistribution(gl1.toDouble(), gl2.toDouble()).inverseCumulativeProbability(1.0 - alfa), 4)



        for (i in 0 until k) {
            val txvMedia = TextView(context)
            val txvDesvio = TextView(context)
            val txvVariancia = TextView(context)
            val txvCoefVariancia = TextView(context)

            txvMedia.text = media[i].toString()
            txvDesvio.text = desvioPadrao[i].toString()
            txvVariancia.text = variancia[i].toString()
            txvCoefVariancia.text = coeficienteVariacao[i].toString()

            b.trMedia?.addView(txvMedia)
            b.trDesvio?.addView(txvDesvio)
            b.trVariancia?.addView(txvVariancia)
            b.trCoefVariancia?.addView(txvCoefVariancia)
        }

        val txvMediaGlobal = TextView(context)
        txvMediaGlobal.text = mediaGlobal.toString()
        b.trMediaGlobal?.addView(txvMediaGlobal)

        var string = ""
        for (i in media) {
            string += "\t\t" + i.toString()
        }
        val message = "Média: \t\t$string" +
                "\nDesvio Padrão: \t$desvioPadrao"

        val a = getListValuesAsStrings(media)

        b.txvAmostrasTitulo?.text = "Média:\nDesvio Padrão:\nVariância:"
        b.txvAmostrasValor?.text = getListValuesAsStrings(media) + "\n" + getListValuesAsStrings(desvioPadrao) + "\n" + getListValuesAsStrings(variancia)

//        val buttonLayoutParams: LinearLayout.LayoutParams =
//            LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
//        buttonLayoutParams.setMargins(50, 10, 0, 0)
//        button.setLayoutParams(buttonLayoutParams)

        val tr = TableRow(context)
        val lp = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(10,10,10,10)
        tr.layoutParams = lp
        val txv1 = TextView(context)
        val txv2 = TextView(context)
        val txv3 = TextView(context)
        val txv4 = TextView(context)
        txv1.text = "FUNCIONOU"
        txv2.text = "CARAIO"
        txv3.text = "DE"
        txv4.text = "ASA"
        tr.addView(txv1)
        tr.addView(txv2)
        tr.addView(txv3)
        tr.addView(txv4)

        b.table?.addView(tr, lp)


    }

    private fun getListValuesAsStrings(media: MutableList<Double>): String {
        var s = ""
        for (i in media) {
            s += "\t \t\t" + i.toString()
        }
        return s
    }

/*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DICResumo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DICResumo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
 */
}