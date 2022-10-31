package com.kgk.task1.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.kgk.task1.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    protected val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    fun moveToNext(activityName: Class<*>, finishCurrent: Boolean, clearStack: Boolean) {
        val intent = Intent(this, activityName)
        if (clearStack)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        if (finishCurrent)
            finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}