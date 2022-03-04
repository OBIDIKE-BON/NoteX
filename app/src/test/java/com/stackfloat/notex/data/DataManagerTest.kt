package com.stackfloat.notex.data

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DataManagerTest {

    @Before
    fun setUp() {
        DataManager.mNotes.clear()
        DataManager.initNotes()
    }

    @Test
    fun addNote() {
        val course = DataManager.mCourses["android_async"]!!
        val noteTitle = "This is a test note"
        val noteText = "This is the body of my test note"
        val noteIndex = DataManager.addNote(course, noteTitle, noteText)
        val note = DataManager.mNotes[noteIndex]
        assertEquals(course, note.course)
        assertEquals(noteTitle, note.title)
        assertEquals(noteText, note.text)
    }

    @Test
    fun findSimilarNotes() {
        val course = DataManager.mCourses["android_async"]!!
        val noteTitle = "This is a test note"
        val noteText1 = "This is the body of my test note"
        val noteText2 = "This is the body of my second test note"

        val index1 = DataManager.addNote(course, noteTitle, noteText1)
        val index2 = DataManager.addNote(course, noteTitle, noteText2)

        val findNote1 = DataManager.findNote(NoteInfo(course, noteTitle, noteText1))
        val findNote1Index= DataManager.mNotes.indexOf(findNote1)
        assertEquals(index1, findNote1Index)

        val findNote2 = DataManager.findNote(NoteInfo(course, noteTitle, noteText2))
        val findNote2Index= DataManager.mNotes.indexOf(findNote2)
        assertEquals(index2, findNote2Index)
    }
}