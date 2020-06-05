package main.java.com.prototype.model;

import com.google.gson.Gson;

public class Node{
    private int nodeId;
    private double r;
    private double c;
    private int demand;
    private boolean isRouted;

    public Node(double r, double c){
        this.r = r;
        this.c = c;
    }

    public Node(double r, double c, int demand){
        this.r = r;
        this.c = c;
        this.demand = demand;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int id){ this.nodeId = id;}

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

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
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