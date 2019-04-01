package com.example.recyclerview;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.recyclerview.backEnd.IWebService;
import com.example.recyclerview.backEnd.StudentsWebService;
import com.example.recyclerview.backEnd.entities.Student;
import com.example.recyclerview.util.ICallback;

import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements InputDialog.InputDialogListener,
        StudentsAdapter.OnItemDismissListener,
        StudentsAdapter.OnItemEditListener {

    private RecyclerView studentsRecyclerView;
    private LinearLayoutManager layoutManager;
    private StudentsAdapter studentsAdapter;

    private final IWebService<Student> webService = new StudentsWebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_button).setOnClickListener(addListener);

        initRecyclerView();
        loadAllItems();
    }

    @Override
    public void itemDismissed(long id) {
        webService.removeEntity(id);
    }

    @Override
    public void ItemEditClick(long id) {
        DialogFragment dialog = new InputDialog();

        Bundle dialogBundle = new Bundle();
        dialogBundle.putLong(InputDialog.ARGUMENT_ID, id);
        dialog.setArguments(dialogBundle);

        dialog.show(getSupportFragmentManager(), InputDialog.EDIT_DIALOG_TAG);
    }

    @Override
    public void applyDialogInfo(Intent intent) {
        if (intent.getStringExtra(InputDialog.EXTRA_TAG).equals(InputDialog.EDIT_DIALOG_TAG)) {
            webService.editEntity(intent.getLongExtra(InputDialog.EXTRA_ID, 0),
                    intent.getStringExtra(InputDialog.EXTRA_NAME),
                    intent.getIntExtra(InputDialog.EXTRA_HW_COUNT, 0));
        } else {
            webService.addEntity(intent.getStringExtra(InputDialog.EXTRA_NAME),
                    intent.getIntExtra(InputDialog.EXTRA_HW_COUNT, 0));
        }

        webService.getEntities(new ICallback<List<Student>>() {
            @Override
            public void onResult(List<Student> result) {
                studentsAdapter.updateItems(result);
            }
        });
    }

    private void loadAllItems() {
        webService.getEntities(new ICallback<List<Student>>() {
            @Override
            public void onResult(List<Student> result) {
                studentsAdapter.addItems(result);
            }
        });
    }

    private void initRecyclerView() {
        studentsRecyclerView = findViewById(R.id.sutents_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(layoutManager);
        studentsAdapter = new StudentsAdapter(this);
        studentsRecyclerView.setAdapter(studentsAdapter);
        studentsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentsRecyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
                return super.animateMove(holder, fromX, fromY, toX, toY);
            }
        });
        new ItemTouchHelper(new ItemTouchCallback(studentsAdapter)).attachToRecyclerView(studentsRecyclerView);
    }

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment dialog = new InputDialog();
            dialog.show(getSupportFragmentManager(), InputDialog.ADD_DIALOG_TAG);
        }
    };
}