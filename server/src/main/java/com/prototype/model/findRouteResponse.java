package main.java.com.prototype.model;

public class findRouteResponse {
    Vehicle[] vehicles;
    double cost;

    public findRouteResponse(Vehicle[] vehicles, double cost){
        this.vehicles = vehicles;
        this.cost = cost;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public double getCost() {
        return cost;
    }
}
