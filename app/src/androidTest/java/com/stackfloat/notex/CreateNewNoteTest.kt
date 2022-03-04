package com.stackfloat.notex

import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.Matchers.*
import org.junit.Rule
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stackfloat.notex.activities.NoteXActivity
import com.stackfloat.notex.data.CourseInfo
import com.stackfloat.notex.data.DataManager
import org.hamcrest.CoreMatchers.equalTo
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest {
    @Rule @JvmField
    val noteListActivityRule = ActivityScenarioRule(NoteXActivity::class.java)

    @Test
    fun createNote() {

        val course = DataManager.mCourses["android_async"]
        val noteTitle = "Test note title"
        val noteText = "This is the body of our test note"
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.spinner_courses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())

        onView(withId(R.id.txt_note_title)).perform(typeText(noteTitle))
        onView(withId(R.id.txt_note_text)).perform(typeText(noteText), closeSoftKeyboard())
        pressBack()

        val lastNote = DataManager.mNotes.last()
        assertEquals(course,lastNote.course)
        assertEquals(noteTitle,lastNote.title)
        assertEquals(noteText,lastNote.text)

    }


}