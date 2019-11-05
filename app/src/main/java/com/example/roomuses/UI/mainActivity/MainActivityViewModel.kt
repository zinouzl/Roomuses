package com.example.roomuses.UI.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.roomuses.model.Note
import com.example.roomuses.room.NoteRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {


    private val noteRepository = NoteRepository(application)

    private val allNotes = noteRepository.getAllNotes()


    fun insert(note: Note){
        noteRepository.insert(note)
    }

    fun delete(note:Note){
        noteRepository.delete(note)
    }

    fun update(note:Note){
        noteRepository.update(note)
    }

    fun deletAllNotes(){
        noteRepository.deleteAllNotes()
    }

    fun getAllNote(): LiveData<List<Note>> {
        return allNotes
    }






}