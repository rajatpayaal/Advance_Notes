package com.example.simple_notes.db.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.simple_notes.db.Notedao;
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
                public abstract Notedao notedao();
}

//            public abstract Notedb notedb();
