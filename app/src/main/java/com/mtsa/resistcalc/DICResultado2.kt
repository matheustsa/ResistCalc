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


class DICResultado2 : AppCompatActivity(), FragDICAnalise.FragmentInterface, FragDICResumo.FragmentInterface, FragDICGraficos.FragmentInterface {

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

    @SuppressLint("SetTextI18n")
    private fun calcularDIC(b: ActDicResultadoAmostrasBinding) {
        val dic = intent.getSerializableExtra("DIC") as DIC

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

//        val txvMedia = TextView(this)
//        val txvDesvio = TextView(this)
//        val txvVariancia = TextView(this)
//        val txvCoefVariancia = TextView(this)

        for (i in 0 until k) {
            val txvMedia = TextView(this)
            val txvDesvio = TextView(this)
            val txvVariancia = TextView(this)
            val txvCoefVariancia = TextView(this)

            txvMedia.text = media[i].toString()
            txvDesvio.text = desvioPadrao[i].toString()
            txvVariancia.text = variancia[i].toString()
            txvCoefVariancia.text = coeficienteVariacao[i].toString()

            b.trMedia?.addView(txvMedia)
            b.trDesvio?.addView(txvDesvio)
            b.trVariancia?.addView(txvVariancia)
            b.trCoefVariancia?.addView(txvCoefVariancia)
        }

        val txvMediaGlobal = TextView(this)
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
        b.txvAmostrasValor1?.text = getListValuesAsStrings(media) + "\n" + getListValuesAsStrings(desvioPadrao) + "\n" + getListValuesAsStrings(variancia)

//        val buttonLayoutParams: LinearLayout.LayoutParams =
//            LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
//        buttonLayoutParams.setMargins(50, 10, 0, 0)
//        button.setLayoutParams(buttonLayoutParams)

        val tr = TableRow(this)
        val lp = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(10,10,10,10)
        tr.layoutParams = lp
        val txv1 = TextView(this)
        val txv2 = TextView(this)
        val txv3 = TextView(this)
        val txv4 = TextView(this)
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
}
