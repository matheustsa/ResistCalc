package com.mtsa.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mtsa.resistcalc.DICResultado2
import com.mtsa.resistcalc.databinding.FragDicAnaliseBinding
import kotlinx.android.synthetic.main.frag_dic__analise.*


class FragDICAnalise : Fragment(), View.OnClickListener {

    private lateinit var bind: FragDicAnaliseBinding

    private var fragmentInterface: FragmentInterface? = null
    private val string = "message from fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragDicAnaliseBinding.inflate(layoutInflater, container, false)


        bind.fragaBt.setOnClickListener(this)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dic = DICResultado2().getValues()
        bind.fragaTxv.text = dic.toString()
    }

    private fun sendToActivity(s: String?) {
        fragmentInterface!!.fragmentInterface(s)
    }

    interface FragmentInterface {
        fun fragmentInterface(s: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInterface) {
            fragmentInterface = context
        } else {
            throw ClassCastException()
        }
    }

    override fun onClick(v: View?) {
        when (v){
            fraga_bt -> {
                sendToActivity(string)
            }
        }
    }
}