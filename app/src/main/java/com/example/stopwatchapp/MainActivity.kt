package com.example.stopwatchapp



import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stopwatchDisplay: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var elapsedTime = 0L
    private var running = false
    private var updateTimer: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatchDisplay = findViewById(R.id.stopwatch_display)
        startButton = findViewById(R.id.start_button)
        pauseButton = findViewById(R.id.pause_button)
        resetButton = findViewById(R.id.reset_button)

        startButton.setOnClickListener {
            if (!running) {
                running = true
                startTime = System.currentTimeMillis() - elapsedTime
                updateTimer = object : Runnable {
                    override fun run() {
                        elapsedTime = System.currentTimeMillis() - startTime
                        updateDisplay()
                        handler.postDelayed(this, 10)
                    }
                }
                handler.post(updateTimer!!)
            }
        }

        pauseButton.setOnClickListener {
            if (running) {
                running = false
                handler.removeCallbacks(updateTimer!!)
            }
        }

        resetButton.setOnClickListener {
            running = false
            handler.removeCallbacks(updateTimer!!)
            elapsedTime = 0
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        val totalMilliseconds = (elapsedTime / 10).toInt()
        val minutes = totalMilliseconds / 6000
        val seconds = (totalMilliseconds % 6000) / 100
        val milliseconds = totalMilliseconds % 100

        stopwatchDisplay.text = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
    }
}
