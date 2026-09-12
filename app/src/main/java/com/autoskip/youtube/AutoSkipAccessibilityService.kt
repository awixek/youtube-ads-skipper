package com.autoskip.youtube

import android.accessibilityservice.AccessibilityService
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Minimal, local-only YouTube Skip Ad clicker.
 *
 * It does not inspect screenshots, use the network, or run a polling timer.
 * It reacts to YouTube accessibility-tree changes only.
 */
class AutoSkipAccessibilityService : AccessibilityService() {

    companion object {
        private const val YOUTUBE_PACKAGE = "com.google.android.youtube"
        private const val CLICK_COOLDOWN_MS = 300L

        // Exact/near-exact labels used by common YouTube UI/locales.
        private val SKIP_LABELS = setOf(
            "skip ad",
            "skip ads",
            "skip"
        )
    }

    private var lastClickAt = 0L

    override fun onAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent?) {
        if (event == null || event.packageName != YOUTUBE_PACKAGE) return

        val type = event.eventType
        if (type != android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED &&
            type != android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return
        }

        val now = SystemClock.uptimeMillis()
        if (now - lastClickAt < CLICK_COOLDOWN_MS) return

        val root = rootInActiveWindow ?: return
        val target = findSkipTarget(root) ?: return

        if (target.isVisibleToUser && target.isEnabled && target.isClickable) {
            if (target.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                lastClickAt = now
            }
        }
    }

    private fun findSkipTarget(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        // Fast path using Android's indexed text search.
        for (label in SKIP_LABELS) {
            val nodes = root.findAccessibilityNodeInfosByText(label)
            for (node in nodes) {
                val target = actionableTarget(node)
                if (target != null) return target
            }
        }

        // Fallback tree walk for versions where text indexing is incomplete.
        return walk(root)
    }

    private fun walk(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (matchesSkipLabel(node)) {
            actionableTarget(node)?.let { return it }
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = walk(child)
            if (result != null) return result
        }
        return null
    }

    private fun matchesSkipLabel(node: AccessibilityNodeInfo): Boolean {
        val text = node.text?.toString()?.trim()?.lowercase()
        val description = node.contentDescription?.toString()?.trim()?.lowercase()

        return SKIP_LABELS.any { label ->
            text == label || description == label
        }
    }

    /**
     * YouTube may expose the label on a non-clickable child while the
     * clickable action is on its parent. Return the actual clickable node.
     */
    private fun actionableTarget(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (!node.isVisibleToUser || !node.isEnabled) return null
        if (node.isClickable) return node

        var parent = node.parent
        repeat(4) {
            if (parent == null) return@repeat
            if (parent.isVisibleToUser && parent.isEnabled && parent.isClickable) {
                return parent
            }
            parent = parent.parent
        }
        return null
    }

    override fun onInterrupt() = Unit
}
