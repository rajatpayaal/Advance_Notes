/*
 * *
 *  * Created by RAJAT PAYAL on 25 MAY 2020
 *  * Copyright (c) 2022.year . All rights reserved.
 *  * Last modified 25 MAY 2020
 *
 *
 */
package com.example.simple_notes.db;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.simple_notes.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
 @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note>getAllNOtes();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

}
