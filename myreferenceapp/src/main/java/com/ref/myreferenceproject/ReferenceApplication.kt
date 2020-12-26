package com.ref.myreferenceproject

import android.app.Application
import android.content.res.Resources

class ReferenceApplication : Application() {

    private val LOG_TAG = "ReferenceApplication"
    companion object {
        lateinit var instance: ReferenceApplication
        lateinit var resources: Resources

    }

    override fun onCreate() {
        super.onCreate()
        instance  = this

    }
}