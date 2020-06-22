package com.mtsa.resistcalc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikhaellopez.circularimageview.CircularImageView
import com.mtsa.resistcalc.databinding.ActAboutMeBinding

class AboutMe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_about_me)

        val b = ActAboutMeBinding.inflate(layoutInflater)
        setContentView(b.root)


        val civ = b.circularImageView
        civ.apply {
            // Set Color
            circleColor = Color.WHITE
            // or with gradient
            circleColorStart = Color.BLACK
            circleColorEnd = Color.RED
            circleColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Set Border
            borderWidth = 10f
            borderColor = Color.BLACK
            // or with gradient
            borderColorStart = Color.BLACK
            borderColorEnd = Color.RED
            borderColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Add Shadow with default param
            shadowEnable = true
            // or with custom param
            shadowRadius = 7f
            shadowColor = Color.RED
            shadowGravity = CircularImageView.ShadowGravity.CENTER
        }
    }
}