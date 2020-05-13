package main.java.com.prototype.service;

import main.java.com.prototype.model.Grid;
import main.java.com.prototype.model.Node;
import org.springframework.beans.factory.annotation.Autowired;

public class RouterServiceImpl implements RouterService{

    @Autowired
    Grid grid;

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public boolean addCustomer(double r, double c) {
        Node node = new Node(r, c);
        return grid.addCustomer(node);
    }

    @Override
    public boolean removeCustomer(double r, double c) {
        Node node = new Node(r, c);
        return grid.removeCustomers(node);
    }

    @Override
    public boolean setSource(double r, double c) {
        Node node = new Node(r, c);
        return grid.setSource(node);
    }

    @Override
    public boolean setVehicles(int vehicles) {
        return grid.setVehicles(vehicles);
    }

    @Override
    public boolean setVehicleCapacity(int capacity) {
        return grid.setVehicleCapacity(capacity);
    }

    @Override
    public Grid findRoute() {
        return null;
    }

    @Override
    public Grid resetGrid() {
        return grid.resetGrid();
    }
}