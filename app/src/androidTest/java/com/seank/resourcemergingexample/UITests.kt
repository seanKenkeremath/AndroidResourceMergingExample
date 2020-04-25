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


    //This test will fail because the id in commontestlib's R does not match
    @Test
    fun checkMainScreenFromCommonTestLib() {
        MainScreen().textView.check(matches(isDisplayed()))
    }

    //This will pass because we are using the R from the app
    @Test
    fun checkMainScreenFromApp() {
        onView(ViewMatchers.withId(R.id.main_text_view)).check(matches(isDisplayed()))
    }


    /*
    We will be able to resolve the strings from Base and App, but not CommonTestLib. When the code is compiled a
    version of R.java is generated for Base that contains gaps in the memory addresses such that shared
    ids will have the same address. However, since CommonTestLib is just an androidTest dependency its
    only compiled for the instrumentation APK which generates its own version of R. So even though CommonTestLib
    has a shared transitive dependency on App, the shared ids in CommonTestLib and the others will not match up.
     */
    @Test
    fun logStringsFromActivity() {

        try {
            Log.d("ResourceTest", "Resolved string from CommonTestLib: " + activityRule.activity.getString(CommonStringUtil.getAppNameResourceId()))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from CommonTestLib with Id: " + getHexString(CommonStringUtil.getAppNameResourceId()))
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


    /*
    This is the same as above except we are using the instrumentation context to try and resolve the strings.
    You'll notice that it's reversed -- we can resolve CommonTestLib's id but not the others. This is because
    we the other two modules are using resource ids from App's R.java which is not compatible with the one generated
    by the instrumentation.
     */
    @Test
    fun logStringsFromInstrumentation() {

        try {
            Log.d("ResourceTest", "Resolved string from CommonTestLib: " + InstrumentationRegistry.getInstrumentation().context.getString(CommonStringUtil.getAppNameResourceId()))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from CommonTestLib with Id: " + getHexString(CommonStringUtil.getAppNameResourceId()))
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

    /*
    This is a way we could convert from one R to the other by resolving the resource name and using it. This would
    theoretically be a way to reference the shared AAR dependency resources from CommonTestLib.
     */
    @Test
    fun convertCommonTestLibResIdToRealResId() {
        val realResName = InstrumentationRegistry.getInstrumentation().context.resources.getResourceEntryName(CommonStringUtil.getAppNameResourceId())
        val realResId = activityRule.activity.resources.getIdentifier(realResName, "string", activityRule.activity.packageName)

        try {
            Log.d("ResourceTest", "Resolved string from CommonTestLib: " + activityRule.activity.getString(realResId))
        } catch (e : Resources.NotFoundException) {
            Log.d("ResourceTest","Unable to resolve string from CommonTestLib with Id: " + getHexString(realResId))
        }

    }
}
