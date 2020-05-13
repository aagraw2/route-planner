package main.java.com.prototype.controller;

import main.java.com.prototype.constants.AppConstants;
import main.java.com.prototype.model.Grid;
import main.java.com.prototype.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = AppConstants.BASE_PATH)
public class RouterApiRestController {
    @Autowired
    RouterService routerService;

    @RequestMapping(path = AppConstants.DISPLAY_GRID_PATH)
    public @ResponseBody Grid displayGrid(){
        return routerService.getGrid();
    }

    @RequestMapping(path = AppConstants.ADD_CUSTOMER_PATH)
    public @ResponseBody boolean addCustomer(
            @RequestParam(value="r", required = true) double r,
            @RequestParam(value="c", required = true) double c){
        return routerService.addCustomer(r, c);
    }

    @RequestMapping(path = AppConstants.REMOVE_CUSTOMER_PATH)
    public @ResponseBody boolean removeCustomer(
            @RequestParam(value="r", required = true) double r,
            @RequestParam(value="c", required = true) double c){
        return routerService.removeCustomer(r, c);
    }

    @RequestMapping(path = AppConstants.SET_SOURCE_PATH)
    public @ResponseBody boolean setSource(
            @RequestParam(value="r", required = true) double r,
            @RequestParam(value="c", required = true) double c){
        return routerService.setSource(r, c);
    }

    @RequestMapping(path = AppConstants.SET_VEHICLES_PATH)
    public @ResponseBody boolean setVehicles(
            @RequestParam(value="vehicles", required = true) int vehicles){
        return routerService.setVehicles(vehicles);
    }

    @RequestMapping(path = AppConstants.SET_VEHICLE_CAPACITY_PATH)
    public @ResponseBody boolean setVehicleCapacity(
            @RequestParam(value="capacity", required = true) int capacity){
        return routerService.setVehicleCapacity(capacity);
    }

    @RequestMapping(path = AppConstants.FIND_ROUTE_PATH)
    public @ResponseBody Grid findRoute(){
        return routerService.findRoute();
    }

    @RequestMapping(path = AppConstants.RESET_GRID_PATH)
    public @ResponseBody Grid resetGrid(){
        return routerService.resetGrid();
    }
}
