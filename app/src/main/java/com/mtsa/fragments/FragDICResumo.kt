package com.mtsa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mtsa.resistcalc.databinding.FragDicResumoBinding

class FragDICResumo : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val b = FragDicResumoBinding.inflate(layoutInflater, container, false)
//        ----------------



//        ----------------
        return b.root
    }

}