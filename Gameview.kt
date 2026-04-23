package com.example.smartballgame

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class GameView(context: Context) : View(context) {

    private var x = 300f
    private var y = 300f
    private var dx = 10f
    private var dy = 10f
    private val radius = 50f

    private var score = 0
    private var speedMultiplier = 1f

    // Line position
    private var dangerLineY = 800f
    private var crossedLine = false

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 60f
    }

    private val linePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw danger line
        canvas.drawLine(0f, dangerLineY, width.toFloat(), dangerLineY, linePaint)

        // Move ball randomly
        x += dx * speedMultiplier
        y += dy * speedMultiplier

        // Bounce from walls
        if (x <= radius || x >= width - radius) {
            dx = -dx
            dy = Random.nextFloat() * 20 - 10 // random direction
        }

        if (y <= radius || y >= height - radius) {
            dy = -dy
            dx = Random.nextFloat() * 20 - 10
        }

        // Check line crossing
        if (y > dangerLineY) {
            crossedLine = true
        }

        // Draw ball
        canvas.drawCircle(x, y, radius, ballPaint)

        // Draw score
        canvas.drawText("Score: $score", 50f, 100f, textPaint)

        // Game Over if not popped in time
        if (crossedLine && y > dangerLineY + 200) {
            val intent = Intent(context, GameOverActivity::class.java)
            intent.putExtra("score", score)
            context.startActivity(intent)
            return
        }

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        // Check if user tapped near ball
        val touchX = event?.x ?: 0f
        val touchY = event?.y ?: 0f

        val distance = Math.hypot((touchX - x).toDouble(), (touchY - y).toDouble())

        if (crossedLine && distance < radius * 2) {
            // POP SUCCESS
            score++

            // Reset ball position
            x = Random.nextInt(100, width - 100).toFloat()
            y = Random.nextInt(100, 500).toFloat()

            dx = Random.nextFloat() * 20 - 10
            dy = Random.nextFloat() * 20 - 10

            crossedLine = false

            // Increase speed gradually
            speedMultiplier += 0.1f
        }

        return true
    }
}
