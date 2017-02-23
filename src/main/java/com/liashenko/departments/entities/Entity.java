package com.liashenko.departments.entities;


public abstract class Entity extends Node {

    protected int id = 0;
    protected String name = "";

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
