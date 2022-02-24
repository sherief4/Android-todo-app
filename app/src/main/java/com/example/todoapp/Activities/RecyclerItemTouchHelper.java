package com.example.todoapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.Adapter;
import com.example.todoapp.R;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private Adapter adapter;

    public RecyclerItemTouchHelper(Adapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this Task?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            adapter.editNote(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if (dX > 0) {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit_24);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.green));
        } else {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete_sweep_24);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.red));
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}

//public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
//    private Adapter recyclerAdapter;
//    public  RecyclerItemTouchHelper(Adapter adapter){
//        super(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT);
//        this.recyclerAdapter = adapter;
//    }
//
//    @Override
//    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        return false;
//    }
//
//    @Override
//    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//    final int position = viewHolder.getAdapterPosition();
//    if(direction == ItemTouchHelper.LEFT){
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(recyclerAdapter.getContext());
//        alertBuilder.setTitle("Delete Note");
//        alertBuilder.setMessage("Are You sure you want to delete this note?");
//        alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                recyclerAdapter.deleteItem(position);
//            }
//        });
//        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                recyclerAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
//            }
//        });
//        AlertDialog dialog = alertBuilder.create();
//       dialog.show();
//    }
//    else{
//        recyclerAdapter.gotToEditIntent();
//    }
//    }
//    public void onChildDraw(Canvas c , RecyclerView recyclerView, RecyclerView.ViewHolder  viewHolder , float dx, float dy , int actionState, boolean isCurrentlyActive){
//        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
//        Drawable icon;
//        ColorDrawable background;
//        View itemView = viewHolder.itemView;
//        int backgroundCornerOffset = 20 ;
//        if(dx > 0){
//           icon = ContextCompat.getDrawable(recyclerAdapter.getContext(), R.drawable.ic_baseline_edit_24);
//            background = new ColorDrawable(ContextCompat.getColor(recyclerAdapter.getContext() , R.color.green));
//        }else {
//            icon = ContextCompat.getDrawable(recyclerAdapter.getContext(), R.drawable.ic_baseline_delete_sweep_24);
//            background = new ColorDrawable(ContextCompat.getColor(recyclerAdapter.getContext() , R.color.red));
//        }
//        int iconMargin= (itemView.getHeight() - icon.getIntrinsicHeight()) /2 ;
//        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//        int iconBottom = iconTop + icon.getIntrinsicHeight();
//        if (dx > 0 ){
//            int iconLeft = itemView.getLeft() + iconMargin ;
//            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
//            icon.setBounds(iconLeft , iconTop, iconRight, iconBottom);
//            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int)dx) + backgroundCornerOffset , itemView.getBottom());
//        }
//        else if(dx < 0){
//            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
//            int iconRight = itemView.getRight() - iconMargin ;
//            icon.setBounds(iconLeft , iconTop, iconRight, iconBottom);
//
//            background.setBounds(itemView.getRight() + ((int) dx) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
//        }else{
//            background.setBounds(0,0,0,0);
//        }
//        background.draw(c);
//        icon.draw(c);
//    }
//}
