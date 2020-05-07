package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTodoDesk extends AppCompatActivity {
    EditText titleTodo, descTodo, dateTodo;
    Button btnSave, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_desk);

        titleTodo = findViewById(R.id.titletodo);
        descTodo = findViewById(R.id.desctodo);
        dateTodo = findViewById(R.id.datetodo);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        // Get values from previous activity
        titleTodo.setText(getIntent().getStringExtra("titletodo"));
        descTodo.setText(getIntent().getStringExtra("desctodo"));
        dateTodo.setText(getIntent().getStringExtra("datetodo"));

        final String keyTodo = getIntent().getStringExtra("keytodo");

        reference = FirebaseDatabase.getInstance().getReference().child("TodoApp").child("Todo" + keyTodo);

        // Save changes on button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert data to databse
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titletodo").setValue(titleTodo.getText().toString());
                        dataSnapshot.getRef().child("desctodo").setValue(descTodo.getText().toString());
                        dataSnapshot.getRef().child("datetodo").setValue(dateTodo.getText().toString());

                        Intent a = new Intent(EditTodoDesk.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        // Delete task if button pressed
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent a = new Intent(EditTodoDesk.this, MainActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
