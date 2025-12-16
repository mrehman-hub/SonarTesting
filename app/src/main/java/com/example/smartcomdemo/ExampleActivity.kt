package com.example.smartcomdemo

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cosmotogether.loading.CosmoPercentageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.format

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val view = findViewById<CosmoPercentageLoader>(R.id.progressView)
        view.updateSubTitle("Test one")
        view.updateMessage("Updating the message")
        findViewById<Button>(R.id.thirtyButton).setOnClickListener {
            view.setProgress(30,false)
        }
        findViewById<Button>(R.id.fiftyButton).setOnClickListener {
            view.setProgress(50,true)
        }
        findViewById<Button>(R.id.eightyButton).setOnClickListener {
            view.setProgress(80,false)
        }
        findViewById<Button>(R.id.hundredButton).setOnClickListener {
            view.setProgress(100,true)
        }
    }
}