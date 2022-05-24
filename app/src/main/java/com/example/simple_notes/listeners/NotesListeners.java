package com.example.simple_notes.listeners;

import com.example.simple_notes.entity.Note;

public interface NotesListeners {

    void onNoteClicked(Note note, int position);
}
