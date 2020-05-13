package main.java.com.prototype.model;

import com.google.gson.Gson;

public class Node{
    private int nodeId;
    private double r;
    private double c;
    private boolean isRouted;

    public Node(double r, double c){
        this.nodeId=0;
        this.r = r;
        this.c = c;
    }

    public Node(int nodeId, double r, double c){
        this.nodeId=nodeId;
        this.r = r;
        this.c = c;
    }

    public int getNodeId() {
        return nodeId;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public boolean isRouted() {
        return isRouted;
    }

    public void setRouted(boolean routed) {
        isRouted = routed;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}