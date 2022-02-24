package com.example.todoapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Activities.EditNoteBottomActivity;
import com.example.todoapp.Activities.MainActivity;

import com.example.todoapp.Activities.ViewNoteActivity;
import com.example.todoapp.db.DataBaseAdapter;
import com.example.todoapp.db.NoteModel;
import com.example.todoapp.R;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<NoteModel> data;
    DataBaseAdapter dataBaseAdapter;
    MainActivity activity;
    ViewNoteActivity viewNoteActivity;
    public Adapter(DataBaseAdapter db , MainActivity activity){
        this.dataBaseAdapter = db;
        this.activity = activity;

    }
    public Adapter(DataBaseAdapter db , ViewNoteActivity activity){
        this.dataBaseAdapter = db;
        this.viewNoteActivity = activity;

    }

    public Adapter(List<NoteModel> data, DataBaseAdapter dataBaseAdapter) {
        this.data = data;
        this.dataBaseAdapter = dataBaseAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteModel model = data.get(position);
        int pos = position;
        holder.title.setText(model.getNoteTitle());
        holder.type.setText(model.getNoteType());
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getContext() , ViewNoteActivity.class);


            intent.putExtra("title", model.getNoteTitle());
            intent.putExtra("type", model.getNoteType());
            intent.putExtra("details", model.getNoteDetails());
            intent.putExtra("id", model.getId());
            intent.putExtra("position", pos);
            getContext().startActivity(intent);
            }
        });
            }



    private Boolean toBoolean(int num) {
        return num != 0;
    }
    public Context getContext(){
        return  activity;
    }

    public void deleteItem(int position){
        NoteModel note = data.get(position);
        dataBaseAdapter.deleteNote(note.getId());
        data.remove(position);
        notifyItemRemoved(position);
    }
    public void setData(List<NoteModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

public void editNote(int position){
        NoteModel note = data.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", note.getId());
        bundle.putString("title",note.getNoteTitle());
        bundle.putString("type",note.getNoteType());
        bundle.putString("details",note.getNoteDetails());
    EditNoteBottomActivity frag = new EditNoteBottomActivity();
    frag.setArguments(bundle);
    frag.show(activity.getSupportFragmentManager(), EditNoteBottomActivity.TAG);
}

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView type;
        public ImageButton viewButton;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            type = itemView.findViewById(R.id.type_tv);
            viewButton = itemView.findViewById(R.id.imageButton);
            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }
}
