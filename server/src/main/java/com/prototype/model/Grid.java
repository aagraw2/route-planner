package main.java.com.prototype.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private  static Grid grid = null;

    private Node source;
    private List<Node> customers = new ArrayList<>();
    private int vehicles;
    private int vehicleCapacity;

    private Grid(){
        this.source = new Node(0,0);
        this.vehicles = 10;
        this.vehicleCapacity = 50;
    }

    public static Grid getGrid() {
        return grid;
    }

    public Node getSource() {
        return source;
    }

    public boolean setSource(double r, double c) {
        this.source = new Node(r,c);
        return true;
    }

    public int getVehicles() {
        return vehicles;
    }

    public boolean setVehicles(int vehicles) {
        this.vehicles = vehicles;
        return true;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public boolean setVehicleCapacity(int vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
        return true;
    }

    public List<Node> getCustomers() {
        return customers;
    }

    public boolean addCustomer(double r, double c, int demand) {

        this.customers.add (new Node(r, c, demand));
        return true;
    }

    public boolean removeCustomers(double r, double c) {
        //TODO: Implement this function
        this.customers.remove(null);
        return true;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Grid initializeGrid() throws Exception {
        if(grid==null){
            grid = new Grid();
        }else{
            throw new Exception("Grid already initialized");
        }
        return grid;
    }

    public Grid resetGrid(){
        this.source = new Node(0,0);
        this.customers.clear();
        this.vehicles = 10;
        this.vehicleCapacity = 50;
        return grid;
    }
}
