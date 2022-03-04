package com.stackfloat.notex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.stackfloat.notex.data.NoteInfo

class NoteListAdapter(context: Context, private val notesList: List<NoteInfo>) :
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            mLayoutInflater.inflate(
                R.layout.note_list_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (notesList.isNotEmpty()) {
            holder.noteTitle.text = notesList[position].course?.title ?: ""
            holder.noteText.text = notesList[position].title
            holder.notePosition= position
        }
    }

    override fun getItemCount() = notesList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.txt_note_item_title)
        val noteText: TextView = itemView.findViewById(R.id.txt_note_item_text)
        var notePosition = 0

        init {
            itemView.setOnClickListener {
//                val noteIntent = Intent(context, MainActivity::class.java)
//                noteIntent.putExtra(NOTE_POSITION, notePosition)
//                context.startActivity(noteIntent)
                val navigateToEditNote = NoteListFragmentDirections.navigateToEditNote(notePosition)
                it.findNavController().navigate(navigateToEditNote)
            }
        }
    }

}