package com.mtsa.resistcalc

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.resistcalc.databinding.ActAboutMeBinding
import kotlinx.android.synthetic.main.act_about_me.*
import org.jetbrains.anko.alert


class Sobre : AppCompatActivity(), View.OnClickListener {

    private lateinit var b: ActAboutMeBinding
    private val linkedin = "https://www.linkedin.com/in/matheus-t-s-abella-680576b3/"
    private val github = "https://github.com/matheustsa"
    private val instagram = "https://www.instagram.com/matheustsa/"
    private val twitter = "https://twitter.com/matheustsa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActAboutMeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.imbtLinkedin.setOnClickListener(this)
        b.imbtGitHub.setOnClickListener(this)
        b.imbtInstagram.setOnClickListener(this)
        b.imbtTwitter.setOnClickListener(this)
    }

    private fun openBrowser(uri: String) {
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(uri)))    }

    override fun onClick(v: View?) {
        when (v) {
            b.imbtLinkedin -> openBrowser(linkedin)
            b.imbtGitHub -> openBrowser(github)
            b.imbtInstagram -> openBrowser(instagram)
            b.imbtTwitter -> openBrowser(twitter)
        }
    }
}