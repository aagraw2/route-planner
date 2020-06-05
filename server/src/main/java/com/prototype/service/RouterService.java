package main.java.com.prototype.service;

import main.java.com.prototype.model.Grid;
import main.java.com.prototype.model.findRouteResponse;

public interface RouterService{
    Grid getGrid();
    boolean addCustomer(double r, double c, int demand);
    boolean removeCustomer(double r, double c);
    boolean setSource(double r, double c);
    boolean setVehicles(int vehicles);
    boolean setVehicleCapacity(int capacity);
    findRouteResponse findRoute(String method) throws Exception;
    Grid resetGrid();
}