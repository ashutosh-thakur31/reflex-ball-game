package com.example.smartballgame

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val score = intent.getIntExtra("score", 0)
        val time = intent.getLongExtra("time", 0)

        val textView = TextView(this).apply {
            text = "GAME OVER\n\nScore: $score\nTime: $time sec"
            textSize = 24f
        }

        setContentView(textView)
    }
}
