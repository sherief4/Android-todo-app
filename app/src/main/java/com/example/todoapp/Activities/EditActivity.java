package com.example.todoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.db.DataBaseAdapter;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    EditText editTitleET;
    EditText editTypeET;
    EditText editDetailsET;
    Button cancelEditBtn;
    Button saveEditBtn;

    DataBaseAdapter dataBaseAdapter;
    int id;
    String title;
    String type;
    String details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Bundle extras = getIntent().getExtras();
        dataBaseAdapter = new DataBaseAdapter(EditActivity.this);
        editTitleET = findViewById(R.id.edit_title_editText);
        editTypeET = findViewById(R.id.edit_type_edit_text);
        editDetailsET = findViewById(R.id.edit_details_edit_text);
        cancelEditBtn = findViewById(R.id.cancel_edit_btn);
        saveEditBtn = findViewById(R.id.save_edit_btn);

        title = extras.getString("title");
        type = extras.getString("type");
        details = extras.getString("details");
        id = extras.getInt("id");

        try {
            editTitleET.setText(title);
            editTypeET.setText(type);
            editDetailsET.setText(details);

        }catch (Exception e){

        }

        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseAdapter.updateNote(id, editTitleET.getText().toString() , editTypeET.getText().toString(), editDetailsET.getText().toString());
                Intent home = new Intent(EditActivity.this , MainActivity.class);
                startActivity(home);
                finish();
            }
        });

        cancelEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}