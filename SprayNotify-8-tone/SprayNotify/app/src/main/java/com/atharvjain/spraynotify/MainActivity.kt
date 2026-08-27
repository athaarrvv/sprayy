package com.atharvjain.spraynotify

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 48, 32, 32) }
        val title = TextView(this).apply { text = "🔊 Spray Notify"; textSize = 28f }
        val info = TextView(this).apply {
            text = "\nGive this app Notification Access. It listens for incoming notifications and plays Spray 1, Spray 2, Spray 3… separately for each chat.\n\nCurrent version: 8 levels, then loops back to Spray 1.\n\nImportant: replace spray1.wav … spray8.wav in app/src/main/res/raw with your own sounds."
            textSize = 16f
        }
        val button = Button(this).apply { text = "Open Notification Access"; setOnClickListener { startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")) } }
        root.addView(title); root.addView(info); root.addView(button)
        setContentView(root)
    }
}
