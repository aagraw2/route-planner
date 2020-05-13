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

    public boolean setSource(Node source) {
        this.source = source;
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

    public boolean addCustomer(Node customer) {
        this.customers.add (customer);
        return true;
    }

    public boolean removeCustomers(Node customer) {
        this.customers.remove(customer);
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
        this.grid = new Grid();
        return grid;
    }
}
