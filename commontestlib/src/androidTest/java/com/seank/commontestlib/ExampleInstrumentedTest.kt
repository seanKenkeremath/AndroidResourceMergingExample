package com.seank.commontestlib

import android.util.Log
import com.seank.baselib.BaseStringUtil

import org.junit.Test

class ExampleInstrumentedTest {
    @Test
    fun logResourceIds() {
        Log.d("ResourceTest", "Common: " + CommonStringUtil.getAppNameResourceId())
        Log.d("ResourceTest", "Base: " + BaseStringUtil.getAppNameResourceId())
    }
}
