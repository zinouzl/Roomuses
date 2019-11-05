package com.example.roomuses.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomuses.R
import com.example.roomuses.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    var notes = listOf<Note>()
        set(notes){
            field = notes
            notifyDataSetChanged()
        }
    var listener:OnItemClickListener? =null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)

        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
      return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.textViewBody.setText(currentNote.description)
        holder.textViewPropriry.setText(currentNote.priority.toString())
        holder.textViewTitle.setText(currentNote.title)
    }


   inner class NoteHolder(itemView: View) :RecyclerView.ViewHolder(itemView){


        val textViewTitle = itemView.text_view_title
        val textViewBody = itemView.text_view_body
        val textViewPropriry = itemView.text_view_priority
        init {
            itemView.setOnClickListener{
                val position  = adapterPosition
                if (RecyclerView.NO_POSITION != position)
                    listener?.onItemClick(notes[position])



            }
        }


    }

    interface OnItemClickListener {
       fun onItemClick(note:Note)
    }


}