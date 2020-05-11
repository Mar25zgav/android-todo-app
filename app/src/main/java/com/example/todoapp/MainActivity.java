package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage, endpage;
    FloatingActionButton btnAddNew;
    DatabaseReference reference;
    ArrayList<Todo> todoList;
    TodoAdapter todoAdapter;
    RecyclerView todo;
    Toast backToast;
    static String userkey;
    long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);

        // If new user -> save id
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        userkey = sharedPreferences.getString("userID", "0");
        if (userkey.equals("0")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            userkey = Integer.toString(new Random().nextInt());
            editor.putString("userID", userkey);
            editor.apply();
        }

        // When floating button pressed open add new task activity
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
        reference = FirebaseDatabase.getInstance().getReference().child("TodoApp" + userkey);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* Get all to-do items */
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Todo t = dataSnapshot1.getValue(Todo.class);
                    todoList.add(t);
                }
                // Create and attach adapter
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

    @Override
    public void onBackPressed() {
        // If player taps back button two times in 2s exit game
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            // Show toast with text
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        // Save time user pressed back button
        backPressedTime = System.currentTimeMillis();
    }
}
