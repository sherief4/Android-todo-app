package com.example.todoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todoapp.Adapter.Adapter;
import com.example.todoapp.R;
import com.example.todoapp.db.DataBaseAdapter;


import java.util.Objects;

public class ViewNoteActivity extends AppCompatActivity {
    TextView titleTV;
    TextView typeTv;
    TextView detailsTV;
    Button deleteBtn;
    Button editBtn;

    int id;
    String title;
    String type;
    String details;

    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Objects.requireNonNull(getSupportActionBar()).hide();
        deleteBtn = findViewById(R.id.delete_btn);
        editBtn = findViewById(R.id.edit_btn);
        titleTV = findViewById(R.id.titleTv);
        typeTv = findViewById(R.id.note_type_tv2);
        detailsTV = findViewById(R.id.note_details_tv);
        Bundle extras = getIntent().getExtras();
        dataBaseAdapter = new DataBaseAdapter(ViewNoteActivity.this);
        title = extras.getString("title");
        type = extras.getString("type");
        details = extras.getString("details");
        id = extras.getInt("id");
        try {
            titleTV.setText(title);
            typeTv.setText(type);
            detailsTV.setText(details);


        } catch (Exception e) {

        }
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseAdapter.deleteNote(id);
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ViewNoteActivity.this, EditActivity.class);
                editIntent.putExtra("title", title);
                editIntent.putExtra("type",type);
                editIntent.putExtra("details", details);
                editIntent.putExtra("id",id);
                startActivity(editIntent);
            }
        });
    }


}