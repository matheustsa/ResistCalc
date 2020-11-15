package com.mtsa.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mtsa.resistcalc.dic.DIC2
import com.mtsa.resistcalc.databinding.FragDicAnaliseBinding


class FragDICAnalise : Fragment(), View.OnClickListener {

    private lateinit var binding: FragDicAnaliseBinding
    private var fragmentInterface: FragmentInterface? = null
    private val string = "message from fragment"
    private lateinit var dic: DIC2

    private fun sendToActivity(s: String?) {
        fragmentInterface!!.sendToActivity(s)
    }

    private fun getDataFromActivity(): String {
        return fragmentInterface!!.getFromActivity()
    }

    private fun getDIC(): DIC2 {
        return fragmentInterface!!.getDICFromActivity()
    }

    interface FragmentInterface {
        fun sendToActivity(s: String?)
        fun getFromActivity(): String
        fun getDICFromActivity(): DIC2
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInterface) {
            fragmentInterface = context
        } else {
            throw ClassCastException()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragDicAnaliseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dic = getDIC()

        binding.SQDentro.text = dic.SQ_Dentro.toString()
        binding.SQEntre.text = dic.SQ_Entre.toString()
        binding.SQTotal.text = dic.SQ_Total.toString()

        binding.glDentro.text = dic.gl1.toString()
        binding.glEntre.text = dic.gl2.toString()
        binding.glTotal.text = dic.glTotal.toString()

        binding.QMDentro.text = dic.QM_Dentro.toString()
        binding.QMEntre.text = dic.QM_Entre.toString()

        binding.Fcalc.text = dic.F_Calculado.toString()
        binding.Fcrit.text = dic.F_Critico.toString()

        binding.k.text = dic.k.toString()
        binding.n.text = dic.n.toString()
        binding.alfa.text = dic.alfa.toString()

        binding.julgamento.text = dic.julgamento
    }



    override fun onClick(v: View?) {
        when (v){
//            fraga_bt -> {
////                sendToActivity(string)
////                println(getDataFromActivity())
//            }
        }
    }
}