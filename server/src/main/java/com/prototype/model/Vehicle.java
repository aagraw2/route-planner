package main.java.com.prototype.model;

import java.util.ArrayList;

public class Vehicle {
    private int vehicleId;
    private ArrayList<Node> Route = new ArrayList<>();
    private int capacity;
    private int load;
    private int currentLocation;
    private boolean isClosed;

    public Vehicle(int id, int cap, Node source)
    {
        this.vehicleId = id;
        this.capacity = cap;
        this.load = 0;
        this.currentLocation = source.getNodeId();
        this.isClosed = false;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public ArrayList<Node> getRoute() {
        return Route;
    }

    public void setRoute(ArrayList<Node> route) {
        Route = route;
    }

    public void addNodeToRoute(Node customer )
    {
        Route.add(customer);
        this.load +=  customer.getDemand();
        this.currentLocation = customer.getNodeId();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isAllowed(int demand)
    {
        return ((load + demand <= capacity));
    }
}
