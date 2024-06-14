package com.example.appmusickotlin.test.viewmodel_livedata

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.appmusickotlin.R

class MainActivity : AppCompatActivity() {
     val viewModel : NewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addButton = findViewById<Button>(R.id.add)
        val view = findViewById<TextView>(R.id.txt)
        viewModel.scoreA.observe(this, Observer {
                newText -> view.text = newText.toString()
        }
        )
        addButton.setOnClickListener {
            viewModel.incremantScore(20)

        }
    }
}