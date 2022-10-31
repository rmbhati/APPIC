package com.kgk.task1.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kgk.task1.R
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {
    protected val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return initView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view)
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View

    abstract fun initData(view: View)

    fun moveToNext(activityName: Class<*>, finishCurrent: Boolean, clearStack: Boolean) {
        val intent = Intent(requireActivity(), activityName)
        if (clearStack)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        if (finishCurrent)
            requireActivity().finish()
    }
}