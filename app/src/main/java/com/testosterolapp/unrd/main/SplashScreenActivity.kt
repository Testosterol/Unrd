package com.testosterolapp.unrd.main

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.shapeofview.shapes.ArcView
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.conversations.FirstScreenActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen_layout)

        val buttonEnter = findViewById<Button>(R.id.enter_splash_screen)
        val buttonSkip = findViewById<Button>(R.id.skip_intro_video)

        val arcLayout = findViewById<ArcView>(R.id.arcLayout)
        if (arcLayout != null) {
            ValueAnimator.ofFloat(0f, 0f, 200f).apply {
                addUpdateListener { animation -> arcLayout.arcHeight = ((animation.animatedValue as Float).toInt()) }
                duration = 5000
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
        }
        val clickListener = View.OnClickListener { view ->
            when (view.getId()) {
                R.id.enter_splash_screen -> startIntroVideo()
            }
        }
        buttonEnter.setOnClickListener(clickListener)

        val clickListenerSkip = View.OnClickListener { view ->
            when (view.getId()) {
                R.id.skip_intro_video -> skipIntroVideo()
            }
        }
        buttonSkip.setOnClickListener(clickListenerSkip)

    }

    private fun skipIntroVideo() {
        startActivity(Intent(this, FirstScreenActivity::class.java))
    }

    private fun startIntroVideo() {
        startActivity(Intent(this, IntroVideoActivity::class.java))
    }
}