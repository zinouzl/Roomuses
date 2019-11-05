package com.example.roomuses.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.roomuses.model.Note

class NoteRepository(application: Application) {


    private val noteDao: NoteDao



    private val allNotes: LiveData<List<Note>>

    init {
        val noteDatabase = AppDatabase(application)
        noteDao = noteDatabase.noteDao()
        allNotes = noteDao.getAllNotes()

    }

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)

    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)

    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNoteAsyncTask(noteDao).execute()

    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }


    class InsertNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {


        override fun doInBackground(vararg params: Note?) {
            params[0]?.let { this.noteDao.insert(it) }
        }

    }


    class UpdateNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {


        override fun doInBackground(vararg params: Note?) {
            params[0]?.let { this.noteDao.update(it) }
        }

    }

    class DeleteNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {


        override fun doInBackground(vararg params: Note?) {
            params[0]?.let { this.noteDao.delete(it) }
        }

    }


    class DeleteAllNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            this.noteDao.deleteAllNotes()
        }


    }

}

