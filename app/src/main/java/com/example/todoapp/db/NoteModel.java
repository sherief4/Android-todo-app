package com.example.todoapp.db;

public class NoteModel {
    private int id ;
    private String noteTitle, noteType, noteDetails;

    public NoteModel(String noteTitle , String noteType , String noteDetails){
        this.noteTitle = noteTitle;
        this.noteType = noteType;
        this.noteDetails = noteDetails;
    }

    public NoteModel() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteDetails() {
        return noteDetails;
    }

    public void setNoteDetails(String noteDetails) {
        this.noteDetails = noteDetails;
    }
}
