package com.seank.baselib

import android.content.Context

object BaseStringUtil {
    fun getAppName(context: Context): String {
        return context.getString(R.string.app_name)
    }

    fun getAppNameResourceId() : Int {
        return R.string.app_name
    }
}