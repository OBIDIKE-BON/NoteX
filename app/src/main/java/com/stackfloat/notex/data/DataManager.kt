package com.stackfloat.notex.data

object DataManager {
    val mCourses = HashMap<String, CourseInfo>()
    val mNotes = ArrayList<NoteInfo>()

    init {
        initCourses()
        initNotes()
    }

    fun addNote(course: CourseInfo, title: String, text: String): Int {
        mNotes.add(NoteInfo(course, title, text))
        return mNotes.lastIndex
    }

    fun findNote(note: NoteInfo): NoteInfo? {
        for (noteInfo in mNotes)
             if (
                note.course == noteInfo.course &&
                note.title.equals(noteInfo.title) &&
                note.text.equals(noteInfo.text)
            )
                 return noteInfo

        return null
    }

    private fun initCourses() {
        val courses = arrayOf(
            CourseInfo(
                "android_intents",
                "Android Programming with Intents"
            ),
            CourseInfo(
                courseId = "android_async",
                title = "Android Async Programming and Services"
            ),
            CourseInfo(
                title = "Java Fundamentals: The Java Language",
                courseId = "java_lang"
            ),
            CourseInfo(
                "java_core",
                "Java Fundamentals: The Core Platform"
            )
        )
        courses.forEach {
            mCourses[it.courseId] = it
        }
    }

    fun initNotes() {
        val notes = arrayOf(
            NoteInfo(
                mCourses["android_intents"]!!, "Dynamic intent resolution",
                "Wow, intents allow components to be resolved at runtime"
            ),
            NoteInfo(
                mCourses["android_intents"]!!, "Delegating intents",
                "PendingIntents are powerful; they delegate much more than just a component invocation"
            ),
            NoteInfo(
                mCourses["android_async"]!!, "Service default threads",
                "Did you know that by default an Android Service will tie up the UI thread?"
            ),
            NoteInfo(
                mCourses["android_async"]!!, "Long running operations",
                "Foreground Services can be tied to a notification icon"
            ),
            NoteInfo(
                mCourses["java_lang"]!!, "Parameters",
                "Leverage variable-length parameter lists"
            ),
            NoteInfo(
                mCourses["java_lang"]!!, "Anonymous classes",
                "Anonymous classes simplify implementing one-use types"
            ),
            NoteInfo(
                mCourses["java_core"]!!, "Compiler options",
                "The -jar option isn't compatible with with the -cp option"
            ),
            NoteInfo(
                mCourses["java_core"]!!, "Serialization",
                "Remember to include SerialVersionUID to assure version compatibility"
            )
        )
        notes.forEach {
            mNotes.add(it)
        }
    }
}