package com.victor.dogbreeds.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)

        start()
        setEvents()
    }

    protected abstract fun start()
    protected abstract fun setEvents()
}