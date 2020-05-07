package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    Context context;
    ArrayList<Todo> todoList;

    public TodoAdapter(Context context, ArrayList<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titletodo.setText(todoList.get(position).getTitletodo());
        holder.desctodo.setText(todoList.get(position).getDesctodo());
        holder.datetodo.setText(todoList.get(position).getDatetodo());

        final String getTitleTodo = todoList.get(position).getTitletodo();
        final String getDescTodo = todoList.get(position).getDesctodo();
        final String getDateTodo = todoList.get(position).getDatetodo();
        final String getKeyTodo = todoList.get(position).getKeytodo();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(context, EditTodoDesk.class);
                a.putExtra("titletodo", getTitleTodo);
                a.putExtra("desctodo", getDescTodo);
                a.putExtra("datetodo", getDateTodo);
                a.putExtra("keytodo", getKeyTodo);
                context.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titletodo, desctodo, datetodo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletodo = itemView.findViewById(R.id.titletodo);
            desctodo = itemView.findViewById(R.id.desctodo);
            datetodo = itemView.findViewById(R.id.datetodo);
        }
    }
}
