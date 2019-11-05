package com.example.roomuses.UI.mainActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomuses.AppConstants
import com.example.roomuses.R
import com.example.roomuses.UI.mainActivity.AddNote.AddEditNoteActivity
import com.example.roomuses.model.Note
import com.example.roomuses.recyclerView.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val addNoteRequestCode = 1
    private val editNoteRequestCode = 2
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private val itemTouchHelperCallback = object : SimpleCallback(0, RIGHT or LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            //To change body of created functions use File | Settings | File Templates.
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


            mainActivityViewModel.delete(noteAdapter.notes[viewHolder.adapterPosition])
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_note_button.setOnClickListener {
            val intent = Intent(it.context, AddEditNoteActivity::class.java)
            startActivityForResult(intent, addNoteRequestCode)
        }
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        recycler_view_note.apply {
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            noteAdapter = NoteAdapter()
            noteAdapter.listener = object : NoteAdapter.OnItemClickListener {
                override fun onItemClick(note: Note) {
                    val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                    intent.putExtra(AppConstants.ID, note.id)
                    intent.putExtra(AppConstants.TITLE, note.title)
                    intent.putExtra(AppConstants.BODY, note.description)
                    intent.putExtra(AppConstants.PRIORITY, note.priority)

                    startActivityForResult(intent, editNoteRequestCode)

                }

            }


            adapter = noteAdapter
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)


        }
        mainActivityViewModel.getAllNote().observe(this, Observer {
            noteAdapter.notes = it
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addNoteRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AppConstants.TITLE)
            val body = data?.getStringExtra(AppConstants.BODY)
            val priority = data?.getIntExtra(AppConstants.PRIORITY, 1)

            val note = Note(title!!, body!!, priority!!)

            mainActivityViewModel.insert(note)
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()

        } else if (requestCode == editNoteRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AppConstants.TITLE)
            val body = data?.getStringExtra(AppConstants.BODY)
            val priority = data?.getIntExtra(AppConstants.PRIORITY, 1)

            val note = Note(title!!, body!!, priority!!)
            note.id = data.getIntExtra(AppConstants.ID, 0)
            mainActivityViewModel.update(note)
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()

        } else

            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete_all_notes) {
            mainActivityViewModel.deletAllNotes()
        }

        return super.onOptionsItemSelected(item)
    }
}
