package com.seank.resourcemergingexample

import android.content.res.Resources
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.seank.baselib.BaseStringUtil
import com.seank.commontestlib.CommonStringUtil
import com.seank.commontestlib.MainScreen
import com.seank.commontestlib.TestUtil.getHexString
import org.junit.Rule
import org.junit.Test

class UITests {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkMainScreenFromCommonTestLib() {
        MainScreen().textView.check(matches(isDisplayed()))
    }

    @Test
    fun checkMainScreenFromApp() {
        onView(ViewMatchers.withId(R.id.main_text_view)).check(matches(isDisplayed()))
    }

    @Test
    fun logStringsFromActivity() {

        try {
            Log.d("ResourceTest", "Resolved string from Common: " + activityRule.activity.getString(CommonStringUtil.getAppNameResourceId()))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from Common with Id: " + getHexString(CommonStringUtil.getAppNameResourceId()))
        }

        try {
            Log.d("ResourceTest", "Resolved string from Base: " + activityRule.activity.getString(BaseStringUtil.getAppNameResourceId()))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from Base with Id: " + getHexString(BaseStringUtil.getAppNameResourceId()))
        }

        try {
            Log.d("ResourceTest", "Resolved string from App: " + activityRule.activity.getString(R.string.app_name))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from App with Id: " + getHexString(R.string.base_string))
        }
    }

    @Test
    fun logStringsFromInstrumentation() {

        try {
            Log.d("ResourceTest", "Resolved string from Common: " + InstrumentationRegistry.getInstrumentation().context.getString(CommonStringUtil.getAppNameResourceId()))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from Common with Id: " + getHexString(CommonStringUtil.getAppNameResourceId()))
        }

        try {
            Log.d("ResourceTest", "Resolved string from Base: " + InstrumentationRegistry.getInstrumentation().context.getString(BaseStringUtil.getAppNameResourceId()))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from Base with Id: " + getHexString(BaseStringUtil.getAppNameResourceId()))
        }

        try {
            Log.d("ResourceTest", "Resolved string from App: " + InstrumentationRegistry.getInstrumentation().context.getString(R.string.app_name))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from App with Id: " + getHexString(R.string.base_string))
        }
    }

    @Test
    fun convertCommonTestLibResIdToRealResId() {
        val realResName = InstrumentationRegistry.getInstrumentation().context.resources.getResourceEntryName(CommonStringUtil.getAppNameResourceId())
        val realResId = activityRule.activity.resources.getIdentifier(realResName, "string", activityRule.activity.packageName)

        try {
            Log.d("ResourceTest", "Resolved string from Common: " + activityRule.activity.getString(realResId))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from Common with Id: " + getHexString(realResId))
        }

    }
}
