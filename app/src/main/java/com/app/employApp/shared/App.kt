package com.app.employApp.shared

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import com.app.employApp.shared.di.KodeinInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        setup()
    }

    private fun setup() {
        KodeinInjector.inject(this)
        AppThreeTen.init(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        @JvmStatic
        fun string(@StringRes idRes: Int): String {
            //TODO: check late Initialize
            return context.getString(idRes)
        }
    }

}