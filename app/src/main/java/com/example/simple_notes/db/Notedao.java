package com.example.simple_notes.db;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.simple_notes.entity.Note;

import java.util.List;

@Dao
public interface Notedao {
 @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note>getAllNOtes();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

}
