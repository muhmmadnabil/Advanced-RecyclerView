package com.muhmmadnabil.recyclerviewtask;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMain;
    final String LINEAR = "Linear";
    final String GRID = "Grid";
    String currentVisibleView = LINEAR;
    ArrayList<String> list;

    FloatingActionButton fbMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rv_main);
        fbMain = findViewById(R.id.floatingActionButton);

        list = getItemsList();

        linearView();

        fbMain.setOnClickListener(view -> {
            if (currentVisibleView.equals(LINEAR)) {
                fbMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_linear_view));
                gridView();
            } else {
                fbMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_grid_view));
                linearView();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvMain);

    }

    private ArrayList<String> getItemsList() {
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 1; i <= 15; i++) {
            list.add("Item" + i);
        }
        return list;
    }

    private void linearView() {
        currentVisibleView = LINEAR;
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter adapter = new ItemAdapter(list);
        rvMain.setAdapter(adapter);
    }

    private void gridView() {
        currentVisibleView = GRID;
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        ItemAdapter adapter = new ItemAdapter(list);
        rvMain.setAdapter(adapter);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(list, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            list.remove(position);
            rvMain.getAdapter().notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(MainActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_light))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .setActionIconTint(ContextCompat.getColor(recyclerView.getContext(), android.R.color.white))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}