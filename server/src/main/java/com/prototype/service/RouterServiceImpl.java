package main.java.com.prototype.service;

import main.java.com.prototype.constants.AppConstants;
import main.java.com.prototype.model.Grid;
import main.java.com.prototype.model.findRouteResponse;
import main.java.com.prototype.utils.DistanceMatrixFinder;
import main.java.com.prototype.utils.RouteSearch;
import org.springframework.beans.factory.annotation.Autowired;

public class RouterServiceImpl implements RouterService{

    @Autowired
    Grid grid;

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public boolean addCustomer(double r, double c, int demand) {
        return grid.addCustomer(r, c, demand);
    }

    @Override
    public boolean removeCustomer(double r, double c) {
        return grid.removeCustomers(r, c);
    }

    @Override
    public boolean setSource(double r, double c) {
        return grid.setSource(r, c);
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
    public findRouteResponse findRoute(String method) throws Exception {
        double[][] distanceMatrix = DistanceMatrixFinder.findDistanceMatrix(grid);
        RouteSearch solution = new RouteSearch(grid.getVehicles(), grid.getVehicleCapacity(), grid.getSource(), grid.getCustomers());

        //ToDo : Remove hardcoded values
        switch(method)
        {
            case AppConstants.GREEDY_SOLUTION:
                return solution.GreedySolution(grid.getSource(), grid.getCustomers(), distanceMatrix);
            case AppConstants.INTRA_ROUTE_LOCAL_SEARCH:
                return solution.IntraRouteLocalSearch(grid.getSource(), grid.getCustomers(), distanceMatrix, 1000000);
            case AppConstants.INTER_ROUTE_LOCAL_SEARCH:
                return solution.InterRouteLocalSearch(grid.getSource(), grid.getCustomers(), distanceMatrix, 1000000);
            case AppConstants.TABU_SEARCH:
                return solution.TabuSearch(grid.getSource(), grid.getCustomers(), distanceMatrix, 200, 10);
            default:
                //ToDo: define custom exception
                throw new Exception("Method not found");
        }
    }

    @Override
    public Grid resetGrid() {
        return grid.resetGrid();
    }
}