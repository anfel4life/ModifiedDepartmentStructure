package com.liashenko.departments.services.nodesService;


public class Node {

    private String nodeType;
    private int nodeId;
    private String nodeName;

    public Node() {
        this.nodeId = 0;
        this.nodeType = NodeGeneratorUtil.ROOT_NODE_TYPE;
        this.nodeName = NodeGeneratorUtil.ROOT_NODE_TYPE;
    }

    public Node(String nodeType, int nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }
}
