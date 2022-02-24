package com.example.todoapp.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.R;
import com.example.todoapp.db.DataBaseAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.zip.Inflater;

public class EditNoteBottomActivity extends BottomSheetDialogFragment {
    public static final String TAG = "Action Bottom Dialog";
    EditText editTitle;
    EditText editType;
    EditText editDetails;
    Button saveBtn;
    Button dismissBtn;
    int id;
    String title;
    String type;
    String details;
    private DataBaseAdapter dataBaseAdapter;

    public static EditNoteBottomActivity newInstance() {
        return new EditNoteBottomActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_note_bottom, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editDetails = getView().findViewById(R.id.edit_details_bottom);
        editTitle = getView().findViewById(R.id.edit_title_bottom);
        editType = getView().findViewById(R.id.edit_type_bottom);
        saveBtn = getView().findViewById(R.id.btn_save);
        dismissBtn = getView().findViewById(R.id.btn_dismiss);

        dataBaseAdapter = new DataBaseAdapter(getActivity());
        final Bundle bundle = getArguments();
        title = bundle.getString("title");
        type = bundle.getString("type");
        details = bundle.getString("details");
        id = bundle.getInt("id");

        editTitle.setText(title);
        editType.setText(type);
        editDetails.setText(details);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseAdapter.updateNote(id, editTitle.getText().toString(), editType.getText().toString(), editDetails.getText().toString());
                dismiss();
            }
        });
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
