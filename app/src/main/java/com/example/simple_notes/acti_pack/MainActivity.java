package com.example.simple_notes.acti_pack;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.simple_notes.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int REQUES_CODE_ADD_NOTE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView newnote =findViewById(R.id.newnote);
        newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), note_activity.class),
                        REQUES_CODE_ADD_NOTE
                );
            }
        });
    }
//    private void getNotes(){
//        @SuppressLint("StaticFieldLeak")
//                class GetNotesTask extends AsyncTask<Void,Void, List>
    }

//
//}

//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