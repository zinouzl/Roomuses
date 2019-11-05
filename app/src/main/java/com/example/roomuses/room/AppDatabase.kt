package com.example.roomuses.room


import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomuses.model.Note


@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    class PopulateDbAsyncTask(appDatabase: AppDatabase) : AsyncTask<Unit, Unit, Unit>() {

        private val noteDao: NoteDao = appDatabase.noteDao()

        override fun doInBackground(vararg params: Unit?) {
            noteDao.insert(Note("title1", "description1", 1))
            noteDao.insert(Note("title2", "description2", 2))
            noteDao.insert(Note("title3", "description3", 3))

        }

    }


    abstract fun noteDao(): NoteDao

    companion object {
        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { PopulateDbAsyncTask(it).execute() }
            }
        }


        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "note_database.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()

    }


}