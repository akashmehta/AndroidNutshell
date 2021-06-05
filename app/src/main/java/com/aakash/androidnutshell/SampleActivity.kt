package com.aakash.androidnutshell

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aakash.androidnutshell.usermodule.ui.UserItemsActivity
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        btnOpen.setOnClickListener { startActivity(Intent(this, UserItemsActivity::class.java)) }
        btnDestroy.setOnClickListener { finish() }
    }
}