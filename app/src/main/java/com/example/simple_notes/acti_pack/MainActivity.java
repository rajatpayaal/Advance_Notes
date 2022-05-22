package com.example.simple_notes.acti_pack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.simple_notes.Database.NoteDatabase;
import com.example.simple_notes.R;
import com.example.simple_notes.acti_pack.note_activity;
import com.example.simple_notes.adapters.NoteAdapter;
import com.example.simple_notes.entity.Note;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int REQUES_CODE_ADD_NOTE = 1;

    private RecyclerView notesRecycleView;
    private List<Note> noteList;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ImageAddNote = findViewById(R.id.ImageAddNote);
        ImageAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), note_activity.class),
                        REQUES_CODE_ADD_NOTE
                );
            }
        });

        notesRecycleView = findViewById(R.id.notesRecycleView);
        notesRecycleView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList);
        notesRecycleView.setAdapter(noteAdapter);

        getNotes();


    }

    private void getNotes() {
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List doInBackground(Void... voids) {
                return NoteDatabase
                        .getNoteDatabase(getApplicationContext())
                        .notedao().getAllNOtes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (noteList.size() == 0) {
                    noteList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();

                } else {
                    noteList.add(0, notes.get(0));
                    noteAdapter.notifyItemInserted(0);

                }
                notesRecycleView.smoothScrollToPosition(0);
            }
//                Log.d("MY_NOTES",notes.toString());


        }
        new GetNotesTask().execute();
    }
}


//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