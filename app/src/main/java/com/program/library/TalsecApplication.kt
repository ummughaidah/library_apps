package com.program.library

import android.app.Application
import android.util.Log
import com.aheaditec.talsec_security.security.api.SuspiciousAppInfo
import com.aheaditec.talsec_security.security.api.Talsec
import com.aheaditec.talsec_security.security.api.TalsecConfig
import com.aheaditec.talsec_security.security.api.ThreatListener

class TalsecApplication : Application(), ThreatListener.ThreatDetected {

    private val TAG: String = "FreeRASP"

    companion object {
        private const val expectedPackageName = "com.program.library"
        private val expectedSigningCertificateHashBase64 = arrayOf(
            "rfTQYaCx6jA31j4nlZ5fymxpkvSLD++kO7CPvHAAj1Y=",
            "fX8soQ0Mf7EoCXeW7Lmo7dVoHotUDgwc3T84+mAep3s="
        )
        private const val watcherMail = "user@example.com"
        private val supportedAlternativeStores = arrayOf(
            "com.sec.android.app.samsungapps"
        )
        private val isProd = true
    }

    override fun onCreate() {
        super.onCreate()

        val config = TalsecConfig.Builder(
            expectedPackageName,
            expectedSigningCertificateHashBase64)
            .watcherMail(watcherMail)
            .supportedAlternativeStores(supportedAlternativeStores)
            .prod(isProd)
            .build()

        ThreatListener(this).registerListener(this)
        Talsec.start(this, config)
    }

    // method log
    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    override fun onRootDetected() {
        printLog("onRootDetected")
//        throw Exception ("onRootDetected")
    }

    override fun onDebuggerDetected() {
        printLog("onDebuggerDetected")
    }

    override fun onEmulatorDetected() {
        printLog("onEmulatorDetected")
    }

    override fun onTamperDetected() {
        printLog("onTamperDetected")
    }

    override fun onUntrustedInstallationSourceDetected() {
        printLog("onUntrustedInstallationSourceDetected")
    }

    override fun onHookDetected() {
        printLog("onHookDetected")
    }

    override fun onDeviceBindingDetected() {
        printLog("onDeviceBindingDetected")
    }

    override fun onObfuscationIssuesDetected() {
        printLog("onObfuscationIssuesDetected")
    }

    override fun onMalwareDetected(p0: MutableList<SuspiciousAppInfo>?) {
        printLog("onMalwareDetected")
    }
}