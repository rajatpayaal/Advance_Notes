/*
 * *
 *  * Created by RAJATPAYAL on 25 MAY 2020
 *  * Copyright (c) 2022.year . All rights reserved.
 *  * Last modified 25 MAY 2020
 *
 *
 */
package com.example.simple_notes.listeners;

import com.example.simple_notes.entity.Note;

public interface NotesListeners {

    void onNoteClicked(Note note, int position);
}
