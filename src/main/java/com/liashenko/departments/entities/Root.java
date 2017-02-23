package com.liashenko.departments.entities;


public class Root extends Entity {

    private int id;
    private String nodeType;

    public Root() {
        this.id = 0;
        this.nodeType = ROOT_NODE_TYPE;
    }

    @Override
    public int getNodeId() {
        return id;
    }

    @Override
    public String getNodeType() {
        return nodeType;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getId() {
        return 0;
    }
}
