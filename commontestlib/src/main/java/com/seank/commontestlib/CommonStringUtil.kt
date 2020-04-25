package com.seank.commontestlib

import android.content.Context

object CommonStringUtil {
    fun getAppName(context: Context): String {
        return context.getString(R.string.app_name)
    }

    fun getAppNameResourceId() : Int {
        return R.string.app_name
    }
}