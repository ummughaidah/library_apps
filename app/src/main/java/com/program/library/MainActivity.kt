package com.program.library

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var overlayPermissionLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // overlay
        // Inisialisasi ActivityResultLauncher
        overlayPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Tangani hasil dari pengaturan izin overlay
            if (Settings.canDrawOverlays(this)) {
                // Jika izin diberikan
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    window.setHideOverlayWindows(true)
                }
                Toast.makeText(this, "Izin overlay diberikan.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin overlay belum diberikan. Silakan aktifkan izin.", Toast.LENGTH_SHORT).show()
            }
        }
        checkDrawOverlayPermission()

        // screen spaying
        applySecureFlags()

        // hide appBar
        supportActionBar?.hide()

        // button go to home activity
        val buttonClick = findViewById<Button>(R.id.button1)
        buttonClick.setOnClickListener {
            // throw RuntimeException("Test Crash")
            // go to page home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // button go to recognition activity
        val buttonClickRecognition = findViewById<Button>(R.id.button2)
        buttonClickRecognition.setOnClickListener {
            // go to page home
            val intent = Intent(this, RecognitionActivity::class.java)
            startActivity(intent)
        }
    }

    // overlay
    @SuppressLint("ObsoleteSdkInt")
    private fun checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Overlay terdeteksi. Silakan nonaktifkan overlay untuk melanjutkan.", Toast.LENGTH_SHORT).show()
                // Jika belum ada izin, minta izin overlay
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                overlayPermissionLauncher.launch(intent)
            } else {
                window.setHideOverlayWindows(true)
            }
        }
    }

    // screen spaying
    private fun applySecureFlags() {
        getApplication().registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityResumed(activity: Activity) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityPaused(activity: Activity) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStopped(activity: Activity) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityDestroyed(activity: Activity) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

        })
    }
}