package com.example.todoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter {

    static DataBaseHelper dbHelper;

    public DataBaseAdapter(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public long insertEntry(NoteModel entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.NOTE_TITLE, entry.getNoteTitle());

        contentValues.put(DataBaseHelper.NOTE_TYPE, entry.getNoteType());
        contentValues.put(DataBaseHelper.NOTE_DETAILS, entry.getNoteDetails());
        long id = db.insert(DataBaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public List<NoteModel> getAllNotes() {
        List<NoteModel> noteList = new ArrayList<>();
        Cursor cur = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            cur = db.query(DataBaseHelper.TABLE_NAME, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        NoteModel newNote = new NoteModel();
                        newNote.setId(cur.getInt(cur.getColumnIndexOrThrow(DataBaseHelper.ID)));
                        newNote.setNoteTitle(cur.getString(cur.getColumnIndexOrThrow(DataBaseHelper.NOTE_TITLE)));
                        newNote.setNoteDetails(cur.getString(cur.getColumnIndexOrThrow(DataBaseHelper.NOTE_DETAILS)));
                        newNote.setNoteType(cur.getString(cur.getColumnIndexOrThrow(DataBaseHelper.NOTE_TYPE)));
                        noteList.add(newNote);
                    } while (cur.moveToNext());
                }
            }

        } catch (Exception e) {
        } finally {
            db.endTransaction();
            cur.close();
        }
        return noteList;
    }


    public void updateNote(int id, String title, String type, String details) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.put(DataBaseHelper.NOTE_TITLE, title);
        contentValues.put(DataBaseHelper.NOTE_TYPE, type);
        contentValues.put(DataBaseHelper.NOTE_DETAILS, details);
        db.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper.ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.ID + "=?", new String[]{String.valueOf(id)});
    }

    static class DataBaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "TODO_DATABASE";
        private static final String TABLE_NAME = "TODO_TABLE";
        private static final String ID = "id";
        private static final String NOTE_TITLE = "title";
        private static final String NOTE_TYPE = "type";
        private static final String NOTE_DETAILS = "details";
        private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE + " TEXT, " + NOTE_TYPE + " TEXT, " + NOTE_DETAILS + " TEXT);";

        public DataBaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            //Drop table if exists then create table again
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            onCreate(sqLiteDatabase);
        }
    }
}