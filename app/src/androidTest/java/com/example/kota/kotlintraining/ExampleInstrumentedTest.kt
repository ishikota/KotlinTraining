package com.example.kota.kotlintraining

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.startsWith
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.example.kota.kotlintraining", appContext.packageName)
    }

    @Test
    @LargeTest
    fun sampleTest() {
        val activity = activityRule.launchActivity(Intent())
        val fragment = activity.supportFragmentManager.findFragmentById(R.id.container)
        val recyclerView = fragment.view?.findViewById(R.id.recycler_view) as RecyclerView

        val idlingResource = LoadingIdlingResource(recyclerView)
        Espresso.registerIdlingResources(idlingResource)
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
                //.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.unregisterIdlingResources(idlingResource);

        onView(withText(startsWith("Fluent"))).check(matches(isDisplayed()))
    }
}

class LoadingIdlingResource(val list: RecyclerView) : IdlingResource {

    var resourceCallback : IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return LoadingIdlingResource::class.java.simpleName
    }

    override fun isIdleNow(): Boolean {
        val idle: Boolean = if (list.adapter != null) list.adapter.itemCount != 0 else false
        if (idle && resourceCallback is IdlingResource.ResourceCallback?) {
            resourceCallback?.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

}
