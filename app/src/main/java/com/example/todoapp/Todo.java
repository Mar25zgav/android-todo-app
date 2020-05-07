package com.example.todoapp;

public class Todo {
    String titletodo, desctodo, datetodo, keytodo;

    public Todo() { }

    public Todo(String titletodo, String desctodo, String datetodo, String keytodo) {
        this.titletodo = titletodo;
        this.desctodo = desctodo;
        this.datetodo = datetodo;
        this.keytodo = keytodo;
    }

    public String getKeytodo() {
        return keytodo;
    }

    public void setKeytodo(String keytodo) {
        this.keytodo = keytodo;
    }

    public String getTitletodo() {
        return titletodo;
    }

    public void setTitletodo(String titletodo) {
        this.titletodo = titletodo;
    }

    public String getDesctodo() {
        return desctodo;
    }

    public void setDesctodo(String desctodo) {
        this.desctodo = desctodo;
    }

    public String getDatetodo() {
        return datetodo;
    }

    public void setDatetodo(String datetodo) {
        this.datetodo = datetodo;
    }
}
