package com.program.library

import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class MyA11yDelegate : View.AccessibilityDelegate() {

    override fun onPopulateAccessibilityEvent(host: View, event: AccessibilityEvent) {
        super.onPopulateAccessibilityEvent(host, event)
        if (event.text.isNotEmpty()) {
            event.text.clear()
            event.text.add("CENSORED")
        }
    }

    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        if (!info.text.isNullOrEmpty()) {
            info.text = "CENSORED"
        }
    }
}