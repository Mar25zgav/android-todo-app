package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTask extends AppCompatActivity {
    EditText titletodo, desctodo, datetodo;
    Button btnSaveTodo, btnCancel;
    DatabaseReference reference;
    Integer todoNum = new Random().nextInt();
    String keytodo = Integer.toString(todoNum);

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);

        titletodo = findViewById(R.id.addtitle);
        desctodo = findViewById(R.id.adddesc);
        datetodo = findViewById(R.id.adddate);

        btnSaveTodo = findViewById(R.id.btnSaveTodo);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("TodoApp").child("Todo" + todoNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titletodo").setValue(titletodo.getText().toString());
                        dataSnapshot.getRef().child("desctodo").setValue(desctodo.getText().toString());
                        dataSnapshot.getRef().child("datetodo").setValue(datetodo.getText().toString());
                        dataSnapshot.getRef().child("keytodo").setValue(keytodo);

                        Intent a = new Intent(NewTask.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(NewTask.this, MainActivity.class);
                startActivity(a);
            }
        });
    }
}
