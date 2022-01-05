package com.app.core.requster

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.fragment.app.FragmentActivity

class ActivityResultRequester(private val activity: FragmentActivity) {

    fun request(intent: Intent, callback: (ActivityResult) -> Unit) {
        RegisterResultConfig.request(intent, activity, callback)
    }
}