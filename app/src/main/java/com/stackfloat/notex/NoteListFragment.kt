package com.stackfloat.notex

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stackfloat.notex.data.DataManager

class NoteListFragment : Fragment() {

    private lateinit var mNotesAdapter: NoteListAdapter
    private lateinit var mListNotes: RecyclerView

    companion object {
        fun newInstance() = NoteListFragment()
    }

    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.note_list_fragment, container, false)
        mListNotes = view.findViewById(R.id.list_notes)
        val notesList = DataManager.mNotes
        mNotesAdapter = NoteListAdapter(requireContext(), notesList)
        mListNotes.layoutManager = LinearLayoutManager(requireContext())
        mListNotes.adapter = mNotesAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        val mNotes = DataManager.mNotes
            with(mNotesAdapter) {
                if (itemCount < mNotes.size)
                notifyItemInserted(mNotes.lastIndex)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val navigateToSettings = NoteListFragmentDirections.navigateToSettings()
                findNavController().navigate(navigateToSettings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}