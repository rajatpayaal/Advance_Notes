/*
 * *
 *  * Created by RAJATPAYAL on 25 MAY 2020
 *  * Copyright (c) 2022.year . All rights reserved.
 *  * Last modified 25 MAY 2020
 *
 *
 */
package com.example.simple_notes.entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date time")
    private String datetime;

    @ColumnInfo(name = "subtitle")
    private String subtitle;

    @ColumnInfo(name = "noteText")
    private String noteText;

    @ColumnInfo(name = "image_path")
    private String image_path;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "weblink")
    private String weblink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }
    @NonNull
    @Override
    public String toString() {
        return title + " : " + datetime;
    }
}
