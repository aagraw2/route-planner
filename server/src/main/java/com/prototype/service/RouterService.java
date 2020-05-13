package main.java.com.prototype.service;

import main.java.com.prototype.model.Grid;

public interface RouterService{
    Grid getGrid();
    boolean addCustomer(double r, double c);
    boolean removeCustomer(double r, double c);
    boolean setSource(double r, double c);
    boolean setVehicles(int vehicles);
    boolean setVehicleCapacity(int capacity);
    Grid findRoute();
    Grid resetGrid();
}