package com.example.simple_notes.acti_pack;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_notes.R;
import com.example.simple_notes.Database.NoteDatabase;
import com.example.simple_notes.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class note_activity extends AppCompatActivity {
private EditText inputeNotetitle ,InputeNote,InputNoteSubtitle,InputeNoteText;
private TextView textDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        ImageView imageback = findViewById(R.id.imageback);
        imageback.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                onBackPressed();
            }


        });

        inputeNotetitle = findViewById(R.id.InputNoteTitle);
        InputNoteSubtitle = findViewById(R.id.InputeNoteSublite);

        InputeNote=findViewById(R.id.InputeNote);
        textDateTime=findViewById(R.id.textDateTime);
        textDateTime.setText(
                new SimpleDateFormat("EEEE,dd MMMM yyy HH:mm a",
                        Locale.getDefault()).format(new Date())
        );

        ImageView imagedone=findViewById(R.id.imageSave);
                imagedone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     saveNote();
                    }
                });
    }

private void saveNote(){
        if(inputeNotetitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note title not empty",Toast.LENGTH_SHORT).show();
            return;

        }else if (InputNoteSubtitle.getText().toString().trim().isEmpty()
        && InputeNote.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note=new Note();
        note.setTitle(inputeNotetitle.getText().toString());
        note.setSubtitle(InputNoteSubtitle.getText().toString());
        note.setNoteText(InputeNoteText.getText().toString());
        note.setDatetime(textDateTime.getText().toString());

        class SaveNoteText extends AsyncTask<Void,Void,Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getNoteDatabase(getApplicationContext()).notedao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }

new SaveNoteText().execute();
}
    }