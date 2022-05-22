package com.example.simple_notes.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_notes.R;
import com.example.simple_notes.entity.Note;

public class NoteAdapter {

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle,textSubtitle, textDateTime;

        NoteViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle=itemView.findViewById(R.id.textTitle);
            textSubtitle=itemView.findViewById(R.id.textSubtitle);
            textDateTime=itemView.findViewById(R.id.textDateTime);
        }
        void setNote(Note note){
            textTitle.setText(note.getNoteText());
            if(note.getSubtitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            }
            else {
                textSubtitle.setText(note.getSubtitle());
            }
            textDateTime.setText(note.getDatetime());


            }
        }

    }
