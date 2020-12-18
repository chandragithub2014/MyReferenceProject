package com.ref.roomdboprs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ref.roomdboprs.dependency.DependencyUtils


class RoomDBOpsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DependencyUtils.setAppContext(this)
    }
}