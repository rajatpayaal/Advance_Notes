/*
 * *
 *  * Created by RAJAT PAYAL on 25 MAY 2020
 *  * Copyright (c) 2022.year . All rights reserved.
 *  * Last modified 25 MAY 2020
 *
 *
 */
package com.example.simple_notes.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.simple_notes.db.NoteDao;
import com.example.simple_notes.entity.Note;

@Database(entities = Note.class,version=1,exportSchema = false)
public abstract  class NoteDatabase extends RoomDatabase {
    private static NoteDatabase noteDatabase;

    public static synchronized NoteDatabase getNoteDatabase(Context context) {
        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(
                    context,
                    NoteDatabase.class,
                    "note.db"
            ).build();
        }
        return noteDatabase;


    }
    public abstract NoteDao notedao();
}

//            public abstract Notedb notedb();
