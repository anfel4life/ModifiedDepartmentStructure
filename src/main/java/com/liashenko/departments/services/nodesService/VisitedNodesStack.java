package com.liashenko.departments.services.nodesService;


import com.liashenko.departments.services.mainDBService.dataSets.Root;

import java.util.LinkedList;

public class VisitedNodesStack {
    private static VisitedNodesStack INSTANCE;
    private static LinkedList<Node> nodesStack;
    private static Node ROOT_NODE;

    private VisitedNodesStack() {
        nodesStack = new LinkedList<Node>();
        ROOT_NODE = new Node();
        nodesStack.add(ROOT_NODE);
    }

    public static VisitedNodesStack getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VisitedNodesStack();
        }
        return INSTANCE;
    }

    public void setNode(Node node) {
        if (node.getNodeId() != nodesStack.peekLast().getNodeId()) {
            nodesStack.add(node);
        }
    }
//
//    public NodeGenerator getPreviousNode(NodeGenerator node) {
//        int lastIndex = nodesStack.lastIndexOf(node);
//        if (lastIndex > 0) {
//            return nodesStack.get(lastIndex - 1);
//        }
//        return null;
//    }

    public void clear() {
        nodesStack.clear();
        nodesStack.add(ROOT_NODE);
    }

    public Node peekLast() {
        return nodesStack.peekLast();
    }
}
