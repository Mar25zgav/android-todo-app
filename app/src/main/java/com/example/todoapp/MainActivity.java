package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnAddNew;
    TextView titlepage, subtitlepage, endpage;
    DatabaseReference reference;
    RecyclerView todo;
    ArrayList<Todo> todoList;
    TodoAdapter todoAdapter;
    Integer randNum = new Random().nextInt();
    String userkey = Integer.toString(randNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);

        // When floating button pressed open add new task
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewTask.class);
                startActivity(a);
            }
        });

        todo = findViewById(R.id.todo);
        todo.setLayoutManager(new LinearLayoutManager(this));
        todoList = new ArrayList<Todo>();

        // Load tasks from db
        reference = FirebaseDatabase.getInstance().getReference().child("TodoApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Todo t = dataSnapshot1.getValue(Todo.class);
                    todoList.add(t);
                }
                todoAdapter = new TodoAdapter(MainActivity.this, todoList);
                todo.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
