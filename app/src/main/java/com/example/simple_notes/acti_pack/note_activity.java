package com.example.simple_notes.acti_pack;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class note_activity extends AppCompatActivity {
private EditText inputeNotetitle ,InputeNote,InputNoteSubtitle, InputeNoteText;
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
    }

//private void saveNote(){
//        if(inputeNotetitle.getText().toString().trim().isEmpty()){
//            Toast.makeText(this,"Note title not empty",Toast.LENGTH_SHORT).show();
//            return;
//
//        }else if (inputeNotetitle.getText().toString())
//}
    }