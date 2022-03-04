package com.stackfloat.notex


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.stackfloat.notex.activities.NoteXActivity
import com.stackfloat.notex.data.DataManager
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NextThroughNoteTest {

    @get:Rule
    val noteListActivity = ActivityTestRule(NoteXActivity::class.java)


    @Test
    fun nextThroughNote() {

        onView(withId(R.id.list_notes))
            .perform(RecyclerViewActions.actionOnItemAtPosition<NoteListAdapter.ViewHolder>(0, click()))
        for (index in 0..DataManager.mNotes.lastIndex) {
            val note = DataManager.mNotes[index]
            onView(withId(R.id.spinner_courses))
                .check(matches(withSpinnerText(note.course?.title)))
            onView(withId(R.id.txt_note_title))
                .check(matches(withText(note.title)))
            onView(withId(R.id.txt_note_text))
                .check(matches(withText(note.text)))
            if (index < DataManager.mNotes.lastIndex) {
                onView(allOf(withId(R.id.action_next), isEnabled()))
                    .perform(click())
            }
        }
        onView(withId(R.id.action_next)).check(matches(not(isEnabled())))
    }
}