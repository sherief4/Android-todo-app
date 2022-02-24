package com.example.todoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.todoapp.R;
import com.example.todoapp.db.DataBaseAdapter;
import com.example.todoapp.db.NoteModel;


import java.util.Objects;

public class NewTaskActivity extends AppCompatActivity {

    EditText titleEdiText;
    EditText typeEditText;
    EditText detailsEditText;
    Button saveBtn;
    Button cancelButton;


    String title;
    String type;
    String details;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Objects.requireNonNull(getSupportActionBar()).hide();


        titleEdiText = findViewById(R.id.new_title_edit_text);
        typeEditText = findViewById(R.id.new_type_edit_text);
        detailsEditText = findViewById(R.id.new_details_edit_text);
        saveBtn = findViewById(R.id.save_btn);
        cancelButton = findViewById(R.id.edit_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEdiText.getText().toString();
                type = typeEditText.getText().toString();
                details = detailsEditText.getText().toString();
                DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(NewTaskActivity.this);
                NoteModel note = new NoteModel();
                try {

                    note.setNoteTitle(title);
                    note.setNoteType(type);
                    note.setNoteDetails(details);
                    dataBaseAdapter.insertEntry(note);
                    dataBaseAdapter.getAllNotes();
                    Toast.makeText(NewTaskActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(NewTaskActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } finally {

                  finish();

                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}