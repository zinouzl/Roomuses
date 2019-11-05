package com.example.roomuses.UI.mainActivity.AddNote

import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.roomuses.AppConstants
import com.example.roomuses.R
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        num_picker_priority.apply {
            minValue = 1
            maxValue = 10
        }



        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra(AppConstants.ID)){
            supportActionBar?.title = "Edit note"
            edit_text_title.setText(intent.getStringExtra(AppConstants.TITLE))
            edit_text_body.setText(intent.getStringExtra(AppConstants.BODY))
            num_picker_priority.value = intent.getIntExtra(AppConstants.PRIORITY,1)
        }else{
            supportActionBar?.title = "Add note"
        }




    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return true





    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.note_save){
            saveNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = edit_text_title.text.toString()
        val body = edit_text_body.text.toString()
        val priority = num_picker_priority.value

        if(title.trim().isEmpty() || body.trim().isEmpty()){
            Toast.makeText(this,"Please insert the title and the body of your note",Toast.LENGTH_SHORT).show()
        }else{
            val data = Intent()
            data.putExtra(AppConstants.TITLE,title)
            data.putExtra(AppConstants.BODY,body)
            data.putExtra(AppConstants.PRIORITY,priority)
            data.putExtra(AppConstants.ID,intent.getIntExtra(AppConstants.ID,0))
            setResult(Activity.RESULT_OK,data)
            finish()
        }


    }
}