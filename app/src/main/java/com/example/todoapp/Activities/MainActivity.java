package com.example.todoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.todoapp.Adapter.Adapter;
import com.example.todoapp.db.DataBaseAdapter;
import com.example.todoapp.db.NoteModel;
import com.example.todoapp.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    RecyclerView homeRecyclerView;
    FloatingActionButton addNewNote;

    TextView tv;

    Intent addNewIntent;
    Adapter adapter;

    List<NoteModel> notes;


    private DataBaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewNote = findViewById(R.id.add_floating_button);
        Objects.requireNonNull(getSupportActionBar()).hide(); //To hide the action bar

        dbAdapter = new DataBaseAdapter(MainActivity.this);


        notes = dbAdapter.getAllNotes();
        Collections.reverse(notes);


        homeRecyclerView = findViewById(R.id.tasks_recycler_view);
        adapter = new Adapter(dbAdapter, MainActivity.this);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        touchHelper.attachToRecyclerView(homeRecyclerView);

        homeRecyclerView.setAdapter(adapter);
        adapter.setData(notes);


        addNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNewIntent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(addNewIntent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ACT", "onResume");
        notes = dbAdapter.getAllNotes();
        Collections.reverse(notes);
        homeRecyclerView.setAdapter(adapter);
        adapter.setData(notes);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        notes = dbAdapter.getAllNotes();
        Collections.reverse(notes);
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }
}