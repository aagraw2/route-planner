package main.java.com.prototype.constants;

public final class AppConstants {

    private AppConstants() {
        // restricting instantiation
    }

    // Paths
    public static final String BASE_PATH = "/router";
    public static final String DISPLAY_GRID_PATH = "/display-grid";
    public static final String ADD_CUSTOMER_PATH = "/add-customer";
    public static final String REMOVE_CUSTOMER_PATH = "/remove-customer";
    public static final String SET_SOURCE_PATH = "/set-source";
    public static final String SET_VEHICLES_PATH = "/set-vehicles";
    public static final String SET_VEHICLE_CAPACITY_PATH = "/set-vehicles-capacity";
    public static final String FIND_ROUTE_PATH= "/find-route";
    public static final String RESET_GRID_PATH= "/reset-grid";

    //Methods
    public static final String GREEDY_SOLUTION = "greedy";
    public static final String INTRA_ROUTE_LOCAL_SEARCH = "intra-rls";
    public static final String INTER_ROUTE_LOCAL_SEARCH = "inter-rls";
    public static final String TABU_SEARCH = "tabu";
}