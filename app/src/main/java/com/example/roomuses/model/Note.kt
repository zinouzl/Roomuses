package com.example.roomuses.model



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "note_table")
class Note(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
     val description: String,
    @ColumnInfo(name = "priority")
     val priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}