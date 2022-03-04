package com.stackfloat.notex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.stackfloat.notex.data.CourseInfo
import com.stackfloat.notex.data.DataManager
import com.stackfloat.notex.data.NoteInfo

class EditNoteFragment : Fragment() {

    private lateinit var  mMenu : Menu
    private var mIsNewNote: Boolean = false
    private var mIsCancel: Boolean = false
    private var mNoteDirection: Int = 0
    private lateinit var mSpinner: Spinner
    private lateinit var editTitle: EditText
    private lateinit var editText: EditText
    private val args: EditNoteFragmentArgs by navArgs()

    private var mNotePosition: Int = POSITION_NOT_SET

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_note_fragment, container, false)

        mSpinner = view.findViewById(R.id.spinner_courses)
        editTitle = view.findViewById(R.id.txt_note_title)
        editText = view.findViewById(R.id.txt_note_text)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
               setHasOptionsMenu(true)
        val courses = DataManager.mCourses.values.toList()
        val spinnerCoursesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            courses
        )
        spinnerCoursesAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        mSpinner.adapter = spinnerCoursesAdapter
        mNotePosition = if (args.noteId!= POSITION_NOT_SET) args.noteId else POSITION_NOT_SET
        if (savedInstanceState != null)
            mNotePosition = savedInstanceState.getInt(NOTE_POSITION, POSITION_NOT_SET)
        if (mNotePosition != POSITION_NOT_SET) {
            displayNote(mNotePosition)
        } else {
            mIsNewNote = true
            createNewNote()
        }

    }

    private fun createNewNote() {
        DataManager.mNotes.add(NoteInfo())
        mNotePosition = DataManager.mNotes.lastIndex
    }

    private fun displayNote(notePosition: Int) {
        val note = DataManager.mNotes[notePosition]
        editTitle.setText(note.title)
        view?.findViewById<EditText>(R.id.txt_note_text)?.setText(note.text)
        val courseIndex = DataManager.mCourses.values.indexOf(note.course)
        mSpinner.setSelection(courseIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_send_mail -> {
                sendMail()
                true
            }
            R.id.action_discard -> {
                mIsCancel = true
                val navigateToNoteList = EditNoteFragmentDirections.navigateToNoteList()
                view?.findNavController()?.navigate(navigateToNoteList)
                true
            }
            R.id.action_next -> {
                mNoteDirection = R.id.action_next
                moveTo()
                true
            }
            R.id.action_previous -> {
                mNoteDirection = R.id.action_previous
                moveTo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendMail() {
        val subject = editTitle.text.toString()
        val text = "check out the course I learnt  @ www.pluralsight.com \"" +
                mSpinner.selectedItem.toString() + " \" \n "
        editText.text.toString()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc2822"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.putExtra(Intent.EXTRA_EMAIL, "boltjumbo@gmail.com")
        startActivity(intent)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.d(TAG_MAIN_ACTIVITY, "onPrepareOptionsMenu: ")
        val nextMenuItem = menu.findItem(R.id.action_next)
        val previousMenuItem = menu.findItem(R.id.action_previous)
        val lastIndex = DataManager.mNotes.lastIndex
        if (mNotePosition >=lastIndex) {
            if (nextMenuItem != null) {
                nextMenuItem.setIcon(R.drawable.ic_baseline_block_24)
                nextMenuItem.isEnabled = false
            }
        }else if(!nextMenuItem.isEnabled){
            nextMenuItem.isEnabled = true
            nextMenuItem.setIcon(R.drawable.ic_baseline_skip_next_24)
        }
        if (mNotePosition <= 0) {
            if (previousMenuItem != null) {
                with(previousMenuItem){
                    setIcon(R.drawable.ic_baseline_block_24)
                    isEnabled = false
                }
            }
        }else if(!previousMenuItem.isEnabled){
            previousMenuItem.isEnabled = true
            previousMenuItem.setIcon(R.drawable.ic_baseline_skip_previous_24)
        }
         mMenu = menu
        return super.onPrepareOptionsMenu(menu)
    }

    private fun moveTo() {
        Log.d(TAG_MAIN_ACTIVITY, "moveTo: ")
        saveNote()
        navigateNotes()
        onPrepareOptionsMenu(mMenu)
    }

    private fun navigateNotes() {
        Log.d(TAG_MAIN_ACTIVITY, "navigateNotes: ")
        mNotePosition = if (mNoteDirection == R.id.action_next) ++mNotePosition else --mNotePosition
        displayNote(mNotePosition)
//        mNote = DataManager.getInstance().getNotes().get(mNotePosition - 1);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            NOTE_POSITION,
            if (mNotePosition == POSITION_NOT_SET) POSITION_NOT_SET else mNotePosition
        )
    }

    override fun onPause() {
        super.onPause()
        if (mIsCancel) {
            if (mIsNewNote) {
                DataManager.mNotes.removeAt(mNotePosition)
            }
        } else saveNote()
    }

    private fun saveNote() {
        val note = DataManager.mNotes[mNotePosition]
        note.title = editTitle.text.toString()
        note.text = editText.text.toString()
        note.course = mSpinner.selectedItem as CourseInfo?
    }
}