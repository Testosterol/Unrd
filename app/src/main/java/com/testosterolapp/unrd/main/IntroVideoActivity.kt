package com.testosterolapp.unrd.main

import android.content.Intent
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.conversations.FirstScreenActivity
import com.testosterolapp.unrd.db.DaoRepository


class IntroVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.intro_video_layout)

        val videoView: VideoView = findViewById(R.id.intro_video_view)
        val daoRepository = DaoRepository(this)

        daoRepository.getIntroVideo(object : DaoRepository.onGetIntroVideoComplete {
            override suspend fun onComplete(uri: String) {
                runOnUiThread {
                    videoView.setVideoPath(uri)
                    videoView.setOnCompletionListener(OnCompletionListener {
                        startActivity(Intent(baseContext, FirstScreenActivity::class.java))
                    })
                    videoView.start()
                }
            }
        })

    }
}