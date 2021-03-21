package com.openclassrooms.realestatemanager

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FragmentInstrumentedTest {

    @get:Rule
    var rule: ActivityScenarioRule<*> = ActivityScenarioRule(MainActivity::class.java)

    private fun delayer() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @Test
    fun goToAllFragment_andCheckEachFragment_isDisplayed() {
        onView(withId(R.id.nav_map))
                .perform(click())
        onView(withId(R.id.map_fragment))
                .check(matches(isDisplayed()))
        delayer()
        onView(withId(R.id.nav_home))
                .perform(click())
        onView(withId(R.id.home_fragment))
                .check(matches(isDisplayed()))
    }

    @Test
    fun goToDetailedHouse(){
        onView(withId(R.id.house_recycler_view))
                .perform(click())
        onView(withId(R.id.detailed_house_fragment))
                .check(matches(isDisplayed()))
    }
}