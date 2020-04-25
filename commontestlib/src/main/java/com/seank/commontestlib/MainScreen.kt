package com.seank.commontestlib

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.withId

class MainScreen {

    val textView
        get() = Espresso.onView(withId(R.id.main_text_view))
}