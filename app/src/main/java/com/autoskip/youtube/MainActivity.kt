package com.autoskip.youtube

import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.content.Intent
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
        }

        layout.addView(TextView(this).apply {
            text = getString(R.string.app_name)
            textSize = 24f
            gravity = Gravity.CENTER
        })

        layout.addView(TextView(this).apply {
            text = "\nEnable the Accessibility Service.\n\nNothing else runs: no network, no analytics, no counters, and no polling timer."
            textSize = 16f
            gravity = Gravity.CENTER
        })

        layout.addView(Button(this).apply {
            text = "Open Accessibility Settings"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        })

        setContentView(layout)
    }
}
