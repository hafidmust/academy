package com.hafidmust.academy.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.hafidmust.academy.R
import com.hafidmust.academy.utils.DataDummy
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class HomeActivityTest{
    private val dummyCourse = DataDummy.generateDummyCourse()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadCourses(){
        delay2Seconds()
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyCourse.size))
    }

    @Test
    fun loadDetailCourse(){
        delay2Seconds()
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delay2Seconds()
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyCourse[0].title)))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(withText("Deadline ${dummyCourse[0].deadline}")))
    }

    @Test
    fun loadModule(){
        delay2Seconds()
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()))
        delay2Seconds()
        onView(withId(R.id.btn_start)).perform(click())
        delay2Seconds()
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailModule(){
        delay2Seconds()
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delay2Seconds()
        onView(withId(R.id.btn_start)).perform(click())
        delay2Seconds()
        onView(withId(R.id.rv_module)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delay2Seconds()
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmark(){
        onView(withText("Bookmark")).perform(click())
        delay2Seconds()
        onView(withId(R.id.rv_bookmark)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_bookmark)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyCourse.size))
    }

    private fun delay2Seconds(){
        try {
            Thread.sleep(2000)
        }catch (e : InterruptedException){
            e.printStackTrace()
        }
    }
}